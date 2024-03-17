export function splitUrl(url){
          const arr=url.split("/");
          let host=arr[2].split(":")[0];
          let port=parseInt(arr[2].split(":")[1]);
          let path=arr.reduce((total, current, index)=>{
                    if(index>2) return total+"/"+current;
                    else return total;
          },"")
          if(path=="") path="/";
          console.log("Host: "+host+"- port: "+port+"- path: "+path)
          return {host:host, port:port, path:path};
}