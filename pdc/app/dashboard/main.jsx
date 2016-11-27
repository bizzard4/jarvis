import React from 'react'
import ReactDOM from 'react-dom'
import SubjectList from './SubjectList'

var test_json = [{"subject":{"subject_id":1,"subject_name":"Test subject"}},{"subject":{"subject_id":2,"subject_name":"Test subject 2"}}];

ReactDOM.render(
  <SubjectList json={test_json} />,
  document.getElementById('app')
);
