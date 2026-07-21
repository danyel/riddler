// frontend/routes.tsx
import {protectRoutes} from '@vaadin/hilla-react-auth';
import MainLayout from "Frontend/views/secured/@layout";
import AdminQuestionsPage from "./views/secured/questions/questions";
import HomeView from "Frontend/views/secured/@index";
import LoginPage from "Frontend/views/login/@index";
import LoginLayout from "Frontend/views/login/@layout";
import AdminIconsPage from "./views/secured/icons/icons";
import {createHashRouter, isRouteErrorResponse, useRouteError} from "react-router-dom";
import AdminQuestionPage from "Frontend/views/secured/questions/{id}";
import ParticipantLayout from "Frontend/views/participant/@layout";
import ParticipantPage from "Frontend/views/participant/@index";
import AdminParticipantsPage from "./views/secured/participants/participants";
import {AdminParticipant} from "./views/secured/participants/participant";
import ProfilePage from "./views/secured/profile/profile";
import AdministrationPage from "./views/secured/administration/administration";
import InvitationPage from "./views/secured/invitation/invitation";
import PublicationsPage from "Frontend/views/secured/publication/publications";
import PublicationPage from "Frontend/views/secured/publication/publication";
import CategoryManagementPage from "Frontend/views/secured/category/categories";
import InterviewWizardView from "Frontend/views/participant/interview-wizard";


function RootErrorBoundary() {
    const error = useRouteError();
    console.error(error);

    if (isRouteErrorResponse(error) && error.status === 404) {
        return (
            <div style={{padding: '2rem', textAlign: 'center'}}>
                <h2>Oops! Page Not Found (404)</h2>
                <p>The page you are looking for does not exist or has moved.</p>
                <a href="/">Go back to Home</a>
            </div>
        );
    }

    return (
        <div style={{padding: '2rem', textAlign: 'center'}}>
            <h2>An Unexpected Error Occurred {isRouteErrorResponse(error)}</h2>
            <p>Something went wrong. Please reload the app.</p>
        </div>
    );
}

export const routes = protectRoutes([
    {
        element: <MainLayout/>,
        errorElement: <RootErrorBoundary/>,
        children: [
            {
                path: '/',
                element: <HomeView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']}
            },
            {
                path: '/participants',
                element: <AdminParticipantsPage/>,
                handle: {rolesAllowed: ['ROLE_USER']} // Client-side check
            },
            {
                path: '/participants/:id',
                element: <AdminParticipant/>,
                handle: {rolesAllowed: ['ROLE_USER']} // Client-side check
            },
            {
                path: '/questions',
                element: <AdminQuestionsPage/>,
                handle: {rolesAllowed: ['ROLE_USER']} // Client-side check
            },
            {
                path: '/questions/:id',
                element: <AdminQuestionPage/>,
                handle: {rolesAllowed: ['ROLE_USER']} // Client-side check
            },
            {
                path: '/icons',
                element: <AdminIconsPage/>,
                handle: {rolesAllowed: ['ROLE_ADMIN']} // Client-side check
            },
            {
                path: '/profile',
                element: <ProfilePage/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']} // Client-side check
            },
            {
                path: '/administrations',
                element: <AdministrationPage/>,
                handle: {rolesAllowed: ['ROLE_ADMIN']} // Client-side check
            },
            {
                path: '/invitations/:invitation_id',
                element: <InvitationPage/>,
                handle: {rolesAllowed: ['ROLE_USER', 'ROLE_ADMIN']} // Client-side check
            },
            {
                path: '/publications',
                element: <PublicationsPage/>,
                handle: {rolesAllowed: ['ROLE_USER']} // Client-side check
            },
            {
                path: '/publications/:id',
                element: <PublicationPage/>,
                handle: {rolesAllowed: ['ROLE_USER']} // Client-side check
            },
            {
                path: `/administrations/categories`,
                element: <CategoryManagementPage/>,
                handle: {rolesAllowed: ['ROLE_USER', 'ROLE_ADMIN']}
            }
        ],
    },
    {
        element: <ParticipantLayout/>,
        errorElement: <RootErrorBoundary/>,
        children: [
            {
                path: '/candidate/:token',
                element: <ParticipantPage/>
            },
            {
                path: '/candidate/interview/:invitation_id',
                element: <InterviewWizardView/>
            }
        ]
    },
    {
        element: <LoginLayout/>,
        children: [
            {
                path: '/login',
                element: <LoginPage/>
            }
        ]
    },
    {
        path: '*',
        element: <RootErrorBoundary/>
    }
]);

export const router = createHashRouter(routes);
export default router;
