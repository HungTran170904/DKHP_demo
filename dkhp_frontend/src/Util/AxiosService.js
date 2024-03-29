import Axios from "axios";

class AxiosService {
    
    constructor() {
        const instance = Axios.create();
        instance.interceptors.response.use(this.handleSucess, this.handleError)
        this.instance = instance;
    }

    handleSucess(res) {
        return res;
    }
    handleError(e) {
        return Promise.reject(e);
    }

    get(url) {
        return this.instance.get(url, { headers: { 'Authorization': localStorage.getItem("Authorization") } });
    }
    post(url, body, isJSON) {
        if(body==undefined) body=null;
        console.log("body",body);
        const headers={'Authorization': localStorage.getItem("Authorization")};
        if(isJSON) headers['Content-Type']="application/json"
        return this.instance.post(url, body, { 'headers': headers})
    }

    put(url, body) {
        return this.instance.put(url, body, { 'headers': { 
            'Authorization': localStorage.getItem("Authorization"),
            'Content-Type': 'application/json' } })
    }

    delete(url) {
        return this.instance.delete(url, { 'headers': { 'Authorization': localStorage.getItem("Authorization") } })
    }
}

export default new AxiosService();