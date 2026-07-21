// frontend/App.tsx

import {AuthProvider} from "./auth";
import {RouterProvider} from "react-router-dom";
import {router} from "./routes";

export default function App() {
    return (
        <AuthProvider>
            <RouterProvider router={router}/>
        </AuthProvider>
    );
}