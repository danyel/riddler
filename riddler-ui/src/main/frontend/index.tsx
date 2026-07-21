import {createRoot} from 'react-dom/client';
import App from "./app";

const root = createRoot(document.getElementById('outlet')!); // or 'root' depending on your index.html
root.render(<App/>);
