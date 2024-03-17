import SseClient from "./SseClient.js";

const sseClient=new SseClient(true);

sseClient.connect({
          url:"http://localhost:8080/api/courses/updateRegNumbers",
          method:"GET",
          headers:{
                    Connection: "keep-alive",
                    Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNzA5MDQ3MjYxLCJleHAiOjE3MTAwNDcyNjF9.Wew-9jdqEZaQucTAlLjjdlyYE73oZV7gAoafaShgaL3KV6SoUxqVeSMnoFwmTqtm8N-tARr_veHspxyXYG-HTg"
          },
          onmessage:(data)=>{
                    console.log("Data",data);
          }
})