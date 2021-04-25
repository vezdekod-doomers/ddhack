import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {Button, Container, Loader, TextArea} from "semantic-ui-react";
import {TicketDao, TicketDaoStatusEnum} from "../api/src";
import ApiManager from "../api/ApiManager";
import history from "../history";

function s(status: TicketDaoStatusEnum): string {
  switch (status as TicketDaoStatusEnum) {
    case TicketDaoStatusEnum.Closed: return 'Завершено'
    case TicketDaoStatusEnum.Open: return 'Новое'
  }
}


const TicketScreen: React.FunctionComponent = () => {
  const params = useParams();
  const [loaded, setLoaded] = useState<'loading' | TicketDao>('loading');
  useEffect(() => {
    ApiManager.ticket.getById({id: parseInt((params as any).id, 10)}).then(value => setLoaded(value))
  }, []);
  const [comment, setComment] = useState('');
  return (
    <Container>
      {loaded === 'loading' ? <Loader active /> : (
       <div style={{paddingTop: '16px', paddingBottom: '16px'}}>
         <p>ID: {loaded.id}</p>
         <p>ФИО: {loaded.fio}</p>
         <p>Сообщение: {loaded.message}</p>
         <p>Коммент: {loaded.comment}</p>
         <p>Телефон: {loaded.phone}</p>
         <p>Статус: {s(loaded.status!)}</p>
         <p>Дата создания: {loaded.createDate!.toISOString()}</p>
         {loaded.closeDate && <p>Дата закрытия: {loaded.closeDate!.toISOString()}</p>}
         {loaded.status === TicketDaoStatusEnum.Open && (
           <>
             <h3>Комментарий:</h3>
             <TextArea value={comment} onChange={event => setComment(event.target.value)} />
             <div />
             <Button onClick={() => {
               setLoaded('loading')
               ApiManager.ticket.close({id: parseInt((params as any).id, 10), comment: comment}).then(() => ApiManager.ticket.getById({id: parseInt((params as any).id, 10)}).then(value => setLoaded(value)));
             }}>Закрыть</Button>
           </>
         )}
       </div>
      )}
      <Button onClick={() => history.goBack()}>Назад</Button>
    </Container>
  );
};

export default TicketScreen;
