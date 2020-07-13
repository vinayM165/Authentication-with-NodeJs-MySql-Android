const db = require('mysql')
const express = require('express')
const app = express()
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

const port = 3000
let connection = db.createConnection({
        host     : 'localhost',
            user     : 'root',
            password : '',
            database : 'android_test'
        })
connection.connect((err)=>{
    if(err){
        console.error(err)
    }else{
        console.log('connected to database.')
    }
})


app.post('/signup',(req,res)=>{
    
       var username = req.body.username
       var  password = req.body.password
    
  connection.query('insert into users Values (?,?)',[username,password],(err,result)=>{
      if(!err && result.affectedRows!=0){
          res.send('inserted successfully')
      }else{
          console.error(err);
      }
  })
           
    
})
app.listen(port,()=>{
    console.log('$erver is listening....')
})
