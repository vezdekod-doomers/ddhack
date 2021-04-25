import * as React from 'react';
import {Route, Router, Switch} from "react-router";
import history from "./history";
import {LoginScreen} from "./screen/LoginScreen";
import {Dimmer, Loader} from "semantic-ui-react";
import InitScreen from "./screen/InitScreen";
import TicketListScreen from "./screen/TicketListScreen";
import TicketScreen from "./screen/TicketScreen";

function App() {
  return (
    <Router history={history}>
      <Switch>
        <Route path={"/"} exact component={InitScreen}/>
        <Route path={"/login"} exact component={LoginScreen}/>
        <Route path={"/admin/:id"} component={TicketScreen} />
        <Route path={"/admin"} component={TicketListScreen} />
      </Switch>
    </Router>
  );
}

export default App;
