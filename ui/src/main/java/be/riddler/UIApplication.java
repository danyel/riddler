package be.riddler;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Meta;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@StyleSheet(Lumo.STYLESHEET)
@Meta(name = "Author", content = "Daniel Noulet")
@PWA(name = "Riddler Application", shortName = "what?")
//@Inline("my-custom-javascript.js")
@Viewport("width=device-width, initial-scale=1")
@BodySize(height = "100vh", width = "100vw")
@PageTitle("Riddler")
@Push(value = PushMode.AUTOMATIC, transport = Transport.WEBSOCKET_XHR)
@SpringBootApplication
public class UIApplication implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(UIApplication.class, args);
    }
}
