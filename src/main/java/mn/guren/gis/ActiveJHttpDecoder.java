package mn.guren.gis;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import io.activej.bytebuf.ByteBuf;
import io.activej.bytebuf.util.ByteBufWriter;
import io.activej.common.collection.Either;
import io.activej.config.Config;
import io.activej.http.AsyncServlet;
import io.activej.http.AsyncServletDecorator;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.http.decoder.DecodeErrors;
import io.activej.http.decoder.Decoder;
import io.activej.inject.Injector;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.ModuleBuilder;
import io.activej.launcher.Launcher;
import io.activej.launchers.http.HttpServerLauncher;
import mn.guren.gis.active.impl.ContactDAO;
import mn.guren.gis.active.impl.ContactDAOImpl;
import mn.guren.gis.active.impl.CountryDAO;
import mn.guren.gis.active.impl.CountryDAOImpl;
import mn.guren.gis.model.Address;
import mn.guren.gis.model.Contact;

import java.util.Map;

import static io.activej.common.collection.CollectionUtils.map;
import static io.activej.http.HttpMethod.GET;
import static io.activej.http.HttpMethod.POST;
import static io.activej.http.decoder.Decoders.ofPost;

public class ActiveJHttpDecoder extends HttpServerLauncher {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final String SEPARATOR = "-";

    private static final Decoder<Address> ADDRESS_DECODER = Decoder.of(Address::new,
            ofPost("title", "")
                    .validate(param -> !param.isEmpty(), "Title cannot be empty")
    );

    private static final Decoder<Contact> CONTACT_DECODER = Decoder.of(Contact::new,
            ofPost("name")
                    .validate(name -> !name.isEmpty(), "Name cannot be empty"),
            ofPost("age")
                    .map(Integer::valueOf, "Cannot parse age")
                    .validate(age -> age >= 18, "Age must not be less than 18"),
            ADDRESS_DECODER.withId("contact-address")
    );

    private static ByteBuf applyTemplate(Mustache mustache, Map<String, Object> scopes) {
        ByteBufWriter writer = new ByteBufWriter();
        mustache.execute(writer, scopes);
        return writer.getBuf();
    }
    //[END REGION_5]

    //[START REGION_4]
    public static void main(String[] args) throws Exception {
        Injector injector = Injector.of(ModuleBuilder.create()
                .bind(Config.class).to(() -> Config.ofClassPathProperties(PROPERTIES_FILE))
                .bind(String.class).to(c -> c.get("phrase"), Config.class)
                .build(), (io.activej.inject.module.Module) new ActiveJHttpDecoder());
        Launcher launcher = new ActiveJHttpDecoder();
        launcher.launch(args);
    }
    //[END REGION_6]

    //[START REGION_6]
    @Provides
    ContactDAO dao() {
        return new ContactDAOImpl();
    }

    @Provides
    CountryDAO countryDAO() {
        return new CountryDAOImpl();
    }


    @Provides
    AsyncServlet mainServlet(ContactDAO contactDAO, CountryDAO countryDAO) {
        Mustache contactListView = new DefaultMustacheFactory().compile("static/ContactList.html");
        return RoutingServlet.create()
                .map("/", request ->
                        HttpResponse.ok200()
                                .withBody(applyTemplate(contactListView, map("contacts", contactDAO.list()))))
                .map(POST, "/add", AsyncServletDecorator.loadBody()
                        .serve(request -> {
                            //[START REGION_3]
                            Either<Contact, DecodeErrors> decodedUser = CONTACT_DECODER.decode(request);
                            //[END REGION_3]
                            if (decodedUser.isLeft()) {
                                contactDAO.add(decodedUser.getLeft());
                            }
                            Map<String, Object> scopes = map("contacts", contactDAO.list());
                            if (decodedUser.isRight()) {
                                scopes.put("errors", decodedUser.getRight().toMap(SEPARATOR));
                            }
                            return HttpResponse.ok200()
                                    .withBody(applyTemplate(contactListView, scopes));
                        }))
                .map(GET, "/country", httpRequest -> HttpResponse.ok200().withJson(countryDAO.list()));
    }
}
