import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { Provider } from 'react-redux'
import store from './data/store'
import AppRouter from './routes/AppRouter'
import { BrowserRouter } from 'react-router-dom';
import { Windmill } from '@windmill/react-ui'
import { ThemeProvider } from "@material-tailwind/react";

ReactDOM.createRoot(document.getElementById('root')).render(

<Windmill>
<BrowserRouter>
<ThemeProvider>
<React.StrictMode>
   <Provider store={store}>
    <AppRouter />
  </Provider>
  
  </React.StrictMode>
  </ThemeProvider>
  </BrowserRouter>
  </Windmill>
)