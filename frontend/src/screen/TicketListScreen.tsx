import React, {useEffect, useState} from 'react'
import { useTable } from 'react-table'
import {
  Button,
  Dropdown,
  FormInput,
  Loader,
  Pagination,
  Table as RTable
} from "semantic-ui-react";
import {TicketDao, TicketDaoStatusEnum} from "../api/src";
import ReactDatePicker from "react-datepicker";
import ApiManager from "../api/ApiManager";
import {useDebouncedEffect} from "../util/useDebounceEffect";
import history from "../history";

interface Props {
  columns: any;
}

function Table({ columns }: Props) {
  const [dat, setDat] = useState<'loading' | TicketDao[]>('loading');
  const [count, setCount] = useState(10);
  const [page, setPage] = useState(0);
  const [from, setFrom] = useState<Date | undefined>(undefined);
  const [to, setTo] = useState<Date | undefined>(undefined);
  const [id, setId] = useState('');
  const [phone, setPhone] = useState('');
  const [statuses, setStatuses] = useState([]);
  const [totalPages, setTotalPages] = useState(0);

  useDebouncedEffect(() => {
    setDat('loading'); }, () => {
    ApiManager.ticket.get({
      count,
      from,
      to,
      id: id.length > 0 ? id : undefined,
      page,
      phone: phone.length > 0 ? phone : undefined,
      statuses: statuses.length > 0 ? statuses : undefined,
    }).then(value => {
      console.log('V ' + JSON.stringify(value));
      setTotalPages(value.totalPages!);
      setDat(value.list!);
    })
  }, 500, [count, from, to, id, page, phone, statuses]);
  useEffect(() => {
    if (page >= totalPages) {
      setPage(0);
    }
  }, [page, totalPages]);
  // Use the state and functions returned from useTable to build your UI
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    prepareRow,
    rows,
  } = useTable(
    {
      columns,
      data: dat === 'loading' ? [] : dat,
    },
  )

  // Render the UI for your table
  return (
    <>
      <span>
        С
        <ReactDatePicker onChange={date => setFrom(date as Date)} value={from?.toISOString() ?? 'Не выбрана'} />
        До
        <ReactDatePicker onChange={date => setTo(date as Date)} value={to?.toISOString() ?? 'Не выбрана'} />
        <Dropdown fluid multiple selection options={[
          {key: TicketDaoStatusEnum.Open, text: TicketDaoStatusEnum.Open, value: TicketDaoStatusEnum.Open},
          {key: TicketDaoStatusEnum.Closed, text: TicketDaoStatusEnum.Closed, value: TicketDaoStatusEnum.Closed},
        ]} onChange={(event, data1) => setStatuses(data1.value as any)} />
      </span>
      <span>
        <FormInput fluid placeholder={"Поиск по ID"}
                   onChange={event => setId(event.target.value)} value={id}/>
        <FormInput fluid icon={'phone'} iconPosition={'left'} placeholder={"Поиск по телефону"}
                   onChange={event => setPhone(event.target.value)} value={phone}/>
      </span>
      {dat !== 'loading' ? (
        <RTable {...getTableProps()}>
          <RTable.Header>
            {headerGroups.map(headerGroup => (
              <RTable.Row {...headerGroup.getHeaderGroupProps()}>
                {headerGroup.headers.map(column => (
                  <RTable.HeaderCell {...column.getHeaderProps()}>{column.render('Header')}</RTable.HeaderCell>
                ))}
              </RTable.Row>
            ))}
          </RTable.Header>
          <RTable.Body {...getTableBodyProps()}>
            {rows.map((row) => {
              prepareRow(row)
              return (
                  <RTable.Row {...row.getRowProps()}>
                    {row.cells.map(cell => {
                      return <RTable.Cell {...cell.getCellProps()}>{cell.render('Cell')}</RTable.Cell>
                    })}
                    <RTable.Cell><Button onClick={() => history.push('/admin/' + row.values.id!)}>Открыть</Button></RTable.Cell>
                  </RTable.Row>
              )
            })}
          </RTable.Body>
        </RTable>
      ) : <Loader active />}
      {/*
        Pagination can be built however you'd like.
        This is just a very basic UI implementation:
      */}
      <span>
        <Pagination defaultActivePage={page} totalPages={totalPages} onPageChange={(e, data) => setPage(data.activePage! as number - 1)} />
        <Button onClick={() => setCount(10)}>10</Button>
        <Button onClick={() => setCount(20)}>20</Button>
        <Button onClick={() => setCount(50)}>50</Button>
      </span>
    </>
  )
}

function TicketListScreen() {
  const columns = React.useMemo(
    () => [
      {
        Header: 'Info',
        columns: [
          {
            Header: 'Id',
            accessor: 'id',
          },
          {
            Header: 'ФИО',
            accessor: 'fio',
          },
          {
            Header: 'Телефон',
            accessor: 'phone',
          },
          {
            Header: 'Сообщение',
            accessor: 'message',
          },
          {
            Header: 'Дата создания',
            accessor: 'createDate',
            Cell({row}: any) {
              console.log('ROW ' + row);
              return <p>{row.original.createDate.toISOString()}</p>;
            }
          },
          {
            Header: 'Дата закрытия',
            accessor: 'closeDate',
            Cell({row}: any) {
              return <p>{row.original.closeDate?.toISOString() ?? ''}</p>;
            }
          },
          {
            Header: 'Статус',
            accessor: 'status',
            Cell({row}: any) {
              switch (row.original.status as TicketDaoStatusEnum) {
                case TicketDaoStatusEnum.Closed: return <p>Завершено</p>
                case TicketDaoStatusEnum.Open: return <p>Новое</p>
              }
            }
          },
        ],
      },
    ],
    []
  )

  return (
      <Table columns={columns} />
  )
}

export default TicketListScreen
