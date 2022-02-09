import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import store from './redux/store';
import { Provider } from 'react-redux';
import {BrowserRouter} from "react-router-dom";
import LocaleProvider from "antd/es/locale-provider";
import plPL from 'antd/lib/locale/pl_PL';
import {ConfigProvider} from "antd";

ReactDOM.render(
      <Provider store={store}>
            <ConfigProvider locale={plPL}>
            <BrowserRouter>
                <App />
            </BrowserRouter>
          </ConfigProvider>
      </Provider>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
//reportWebVitals();
