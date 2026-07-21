import {useRef} from 'react';
import {useAuth} from 'Frontend/auth';
import {LoginOverlay, LoginOverlayElement} from "@vaadin/react-components";
import {Navigate} from "Frontend/util/navigate";

export default function LoginView() {
    const {login} = useAuth(); // 'authenticate' updates the AuthProvider state
    const loginOverlayRef = useRef<LoginOverlayElement>(null);
    //
    // // If already logged in, redirect to home
    // if (state.user) {
    //     return <Navigate to="/" replace/>;
    // }

    return (
        <LoginOverlay
            ref={loginOverlayRef}
            opened
            noForgotPassword
            title="Riddler App"
            onLogin={async (e) => {
                const {username, password} = e.detail;

                // This calls your Java UserEndpoint.authenticate AND
                // tells Hilla to manage the resulting UserInfo object.
                const result = await login(username, password);

                if (result.error) {
                    if (loginOverlayRef.current) {
                        loginOverlayRef.current.disabled = false;
                    }
                } else {
                    Navigate.path('/');
                }
            }}
        />
    );
}