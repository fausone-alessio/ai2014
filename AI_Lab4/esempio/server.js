var express = require('express');
var app = express();

port = process.argv[2] || 8000;

app.configure(function () {
    app
	.use(
        "/", //the URL throught which you want to access to you static content
        express.static(__dirname) //where your static content is located in your filesystem
    )
    .use(express.bodyParser());

});

 
var persons = require('./data/persons.json');


app.get('/persons', function  (req, res) {
  res.json(persons);
});

app.get('/persons/:personId', function (req, res) {
  if (typeof persons[req.params.personId] === 'undefined') {
    res.json(404, {status: 'not found - invalid personId'});
  } else {
    res.json(persons[req.params.personId]);
  }
});

app.post('/persons/:personId', function (req, res) {
  var p = req.body;
  console.dir(req.params.personId);
  console.dir(p);
  if (typeof p === 'undefined') {
    res.json(404, {status: 'not found - invalid personId'});
  } else {
    res.json(persons[req.params.personId]=p);
  }
});

app.post('/persons', function  (req, res) {
  var p = req.body;
  console.dir(p);
  
  if(! (typeof p === 'undefined')) {
    persons.push(p);
    res.json(p);
  } else {
    res.status(404).end();
  }
});

app.listen(port); //the port you want to use
console.log("Express server running at => http://localhost:" + port + "/\nCTRL + C to shutdown");
