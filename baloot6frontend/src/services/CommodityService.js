import axios from "axios";

const Commodity_API = "http://localhost:8080/commodities";

class CommodityService{
    getCommodities(){
        return axios.get(Commodity_API);
    }
}
export default new CommodityService();