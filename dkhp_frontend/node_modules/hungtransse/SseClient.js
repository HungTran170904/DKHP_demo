import http from "http"
import {splitUrl } from "./util.js";
import { setTimeout } from "timers/promises";
export default class SseClient{
          connect(params){
                    const {url,headers, onmessage, onclose, onerror}=params;
                    if(!url) throw Error("Url for server-sent event connection is required");
                    const urlParams=splitUrl(url);
                    const options={
                              host: urlParams.host,
                              port: urlParams.port,
                              path: urlParams.path,
                              headers: headers
                    }
                    this.req=http.get(options, res=>{
                              if(onmessage)
                                        res.on("data",(raw)=>{
                                                  const data=raw.toString();
                                                  if(data!="\n\n"&& data!="data:")
                                                            onmessage(""+data.toString());
                                        })
                              if(onclose)
                                        res.on("end",()=>{
                                                  onclose();
                                        })
                                        res.on("error",(err)=>{
                                                  if(onerror) onerror(err);
                                                  else throw err;
                                        })
                    })
                    this.req.on("error",(err)=>{
                             throw err;
                    })
          }
          close(){
                    if(this.req) this.req.end();
          }
}
