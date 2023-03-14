import React, {useState} from 'react';
import PropTypes from 'prop-types';

export default function Login({setToken}) {

    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const handleSubmit = async e => {
        e.preventDefault();
        const tokenRaw = await fetch('http://localhost:8080/authentication', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "login": username,
                "password": password
            })
        })
            .then(data => data.json())

        console.log("!!!",tokenRaw)
        const token = JSON.stringify(tokenRaw);
        if (token.includes("Bearer")) {

            setToken(tokenRaw.data);
        }
    }

    return (
        <div className="login-wrapper">
            <h1>Please Log In</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    <p>Username</p>
                    <input type="text" onChange={e => setUserName(e.target.value)}/>
                </label>
                <label>
                    <p>Password</p>
                    <input type="password" onChange={e => setPassword(e.target.value)}/>
                </label>
                <div>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>
    )
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};