import axios from "axios";

const USERS_API = "http://localhost:8080/users";
const CART_API = "http://localhost:8080/cart";
const HISTORY_API = "http://localhost:8080/history";

class UserService {
    getUsers(){
        return axios.get(USERS_API);
    }
    getUserBuyList(){
        return axios.get(CART_API);
    }
    getUserPurchasedList() {
        return axios.get(HISTORY_API);
    }
}

export default new UserService();