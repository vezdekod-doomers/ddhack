import * as React from "react";
import LoginView from "../view/LoginView";

type State = {
    login: string,
    password: string,
    error?: string,
    loading: boolean
}

export class LoginScreen extends React.Component<any, State> {
    constructor(props: any, context: any) {
        super(props, context);
        this.state = {
            login: "",
            password: "",
            loading: false
        };
        this.login = this.login.bind(this);
    }

    login() {
        this.setState({loading: true});
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/perform_login?username=" + this.state.login + "&password=" + this.state.password);
        xhr.onload = () => {
            if(xhr.status === 200) {
                window.location.href = xhr.responseURL;
            } else {
                let response;
                if(xhr.responseText.startsWith("{"))
                    response = JSON.parse(xhr.responseText).message;
                else
                    response = xhr.responseText;
                this.setState({error: response, loading: false})
            }
        };
        xhr.send();
    }

    render() {
        return (
            <LoginView loading={this.state.loading} error={this.state.error} login={this.state.login} password={this.state.password} onLoginChanged={e => this.setState({login: e})} onPasswordChanged={e => this.setState({password: e})} onLogin={this.login}/>
        );
    }
}