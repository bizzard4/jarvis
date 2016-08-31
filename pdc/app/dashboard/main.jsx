var React = require("react");
var ReactDOM = require("react-dom");
var TagList = require("./components/TagList.jsx");

function render(){
    ReactDOM.render(<TagList />, document.getElementById("container"));    
}
render();