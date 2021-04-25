import * as React from "react";
import {Button, Form, FormInput, Grid, GridColumn, Header, Message, MessageHeader, Segment} from "semantic-ui-react";

export type LoginViewProps = {
    login: string,
    password: string,
    onLoginChanged: (login: string) => void,
    onPasswordChanged: (login: string) => void
    onLogin: () => void,
    error?: string,
    loading: boolean
}

export class LoginView extends React.PureComponent<LoginViewProps> {
    render() {
        return (
            <Grid textAlign={"center"} style={{height: "100vh"}} verticalAlign={"middle"}>
                <GridColumn style={{maxWidth: 450}}>
                    <Header as={"h2"} color={"teal"} textAlign={"center"}>
                        Войдите в свой аккаунт
                    </Header>
                    <Form size={"large"}>
                        <Segment stacked>
                            <FormInput fluid icon={"user"} iconPosition={"left"} placeholder={"Login"} onChange={event => this.props.onLoginChanged(event.target.value)} value={this.props.login}/>
                            <FormInput fluid icon={"lock"} iconPosition={"left"} placeholder={"Password"} type={"password"} onChange={event => this.props.onPasswordChanged(event.target.value)} value={this.props.password}/>
                            <Button loading={this.props.loading} color={"teal"} fluid size={"large"} onClick={this.props.onLogin}>
                                Войти
                            </Button>
                        </Segment>
                    </Form>
                    {this.props.error && <Message negative>
                        <MessageHeader>Ошибка</MessageHeader>
                        <p>{this.props.error}</p>
                    </Message>}
                </GridColumn>
            </Grid>
        );
    }
}

export default LoginView;
