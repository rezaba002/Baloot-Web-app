import axios from "axios";

const PROVIDERS_API = "http://localhost:8080/providers";

class ProviderService {
    getProviders(){
        return axios.get(PROVIDERS_API);
    }
}

export default new ProviderService();