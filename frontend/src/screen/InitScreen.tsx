import React, {useCallback, useState} from 'react';
import {
  Button,
  Container,
  Form,
  FormInput,
  Grid, GridColumn,
  Header,
  Menu,
  Message,
  MessageHeader,
  Segment, TextArea
} from "semantic-ui-react";
import {Link} from 'react-router-dom';
import ReactInputMask from "react-input-mask";
import {TicketControllerApi} from "../api/src";
import ApiManager from "../api/ApiManager";
import history from "../history";

const InitScreen: React.FunctionComponent = () => {
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(undefined);
  const [fio, setFio] = useState('');
  const [phone, setPhone] = useState('');
  const [message, setMessage] = useState('');
  const onSend = useCallback(() => {
    setLoading(true);
    ApiManager.ticket.put({putTicketDao: {fio, phone, message}})
      .then(() => {
        setLoading(false);
        setSuccess(true);
        setTimeout(() => setSuccess(false), 5000);
      })
      .catch(reason => setError(reason.message))
  }, [fio, phone, message]);
  return (
    <Container>
      <Menu>
        <Menu.Menu position={'right'}>
          <Menu.Item onClick={() => history.push('/login')} name={'login'}>Login</Menu.Item>
        </Menu.Menu>
      </Menu>
      <Grid textAlign={"center"} style={{height: "100vh"}} verticalAlign={"middle"}>
        <GridColumn style={{maxWidth: 450}}>
          <Header as={"h2"} color={"teal"} textAlign={"center"}>
            Напишите отзыв
          </Header>
          <Form size={"large"}>
            <Segment stacked>
              <FormInput fluid icon={"user"} iconPosition={"left"} placeholder={"ФИО"}
                         onChange={event => setFio(event.target.value)} value={fio}/>
              <FormInput fluid icon={"phone"} iconPosition={"left"} placeholder={"Телефон"}
                         onChange={event => setPhone(event.target.value)}
                         input={(props) => <ReactInputMask placeholder={'Телефон'} mask={'+9-999-999-99-99'} {...props} value={phone} onChange={event => setPhone(event.target.value)} />}
                         value={phone}/>
              <TextArea  placeholder={"Сообщение"}
                         onChange={event => setMessage(event.target.value)}
                         value={message}/>
            </Segment>
            <Button loading={loading} color={"teal"} fluid size={"large"} onClick={onSend}>
              Отправить
            </Button>
          </Form>
          {error && (
            <Message negative>
              <MessageHeader>Ошибка</MessageHeader>
              <p>{error}</p>
            </Message>
          )}
          {!loading && success && (
            <Message positive>
              <MessageHeader>Успех</MessageHeader>
              <p>Ваше обращение отправлено</p>
            </Message>
          )}
        </GridColumn>
      </Grid>
    </Container>
  );
};

export default InitScreen;
