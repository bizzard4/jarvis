import React from 'react';

class SubjectElement extends React.Component {
	render() {
		return <li><a onClick={this.changeEntry}>{this.props.subject_json.subject_name}</a></li>;
	}

	changeEntry() {
		console.log("tst");
	}
}

class SubjectList extends React.Component {
  render() {
    return (
    	<div>
    		<h1>Subject list</h1>
    		<ol>
    			<SubjectElement subject_json={this.props.json[0].subject} />
    			<SubjectElement subject_json={this.props.json[1].subject} />
    		</ol>
    	</div>
    	);
  }
}

export default SubjectList;