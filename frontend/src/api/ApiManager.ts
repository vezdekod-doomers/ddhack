import {Configuration, TicketControllerApi} from "./src";

class ApiManager {
  static ticket = new TicketControllerApi(new Configuration({basePath: 'http://' + window.location.host}))
}

export default ApiManager;
