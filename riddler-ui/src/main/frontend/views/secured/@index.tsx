import {ViewConfig} from '@vaadin/hilla-file-router/types.js';

export default function HomeView() {
    return (<div>Home</div>);
}

export const config: ViewConfig = {
    title: 'Home',
    loginRequired: true
}