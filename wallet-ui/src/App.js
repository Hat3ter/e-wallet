import React, {useState} from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import "bootstrap-icons/font/bootstrap-icons.css";
import WalletList from "./components/wallet/WalletList";
import {connect} from "react-redux";
import Login from "./components/authentication/Login";

function App() {

    const [token, setToken] = useState();

    if (!token) {
        return <Login setToken={setToken}/>
    }


    return (
        <div className="App">
            <div className="container-sm">
                <div className="row">
                    <div className="col">
                        <WalletList setToken={setToken} token={token}/>
                    </div>
                </div>

            </div>

        </div>
    );
}

export default connect()(App);
