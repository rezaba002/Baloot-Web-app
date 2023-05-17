import React from "react";
import {Button} from "@mui/material";

import LoginRegisterCard from "../cards/LoginRegisterCard";
import axios from "axios";
import CommodityService from "../services/CommodityService";
import AddToCartCard from "../cards/AddToCartCard";

const SINGLE_PROVIDER_API = "http://localhost:8080/provider";
const SINGLE_COMMODITY_API = "http://localhost:8080/commodity";
const COMMENTS_API = "http://localhost:8080/comments";
const SUGGESTED_API = "http://localhost:8080/suggested";
const COMMODITIES_SEARCH_API = "http://localhost:8080/searchCommodity"
const COMMODITIES_SORT_NAME_API = "http://localhost:8080/sortNameCommodity"
const COMMODITIES_SORT_PRICE_API = "http://localhost:8080/sortPriceCommodity"


class CommodityComponent extends React.Component {
    constructor(props) {
        super(props);

        this.handleSearchButton = this.handleSearchButton.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.searchByCategory = this.searchByCategory.bind(this);
        this.searchByName = this.searchByName.bind(this);
        this.searchByProvider = this.searchByProvider.bind(this);
        this.sortByName = this.sortByName.bind(this);
        this.sortByPrice = this.sortByPrice.bind(this);
        this.clearButton = this.clearButton.bind(this);

        this.state = {
            currentUserNull: false,
            commodities: [],
            showSearch: false,
            searchType: "name",
            searchValue: "",
            showNameSort: false,
            showPriceSort: false,
            sortType: ""
        }
    }

    loadCommodity(commodityId, providerId) {
        axios.post(SINGLE_COMMODITY_API, commodityId)
            .then(response => {
                localStorage.setItem('commodity', JSON.stringify(response.data));
            })
        axios.post(SINGLE_PROVIDER_API, providerId)
            .then(response => {
                localStorage.setItem('commodityProviderName', response.data.name);
            })
        axios.post(COMMENTS_API, commodityId)
            .then(response => {
                localStorage.setItem("commodityComments", JSON.stringify(response.data));
            })
        axios.post(SUGGESTED_API, commodityId)
            .then(response => {
                localStorage.setItem("commoditySuggested", JSON.stringify(response.data));
            })

        let path = "/commodities/" + commodityId;

        setTimeout(() => {
            window.location.replace("http://localhost:3000" + path);
        }, 88);
    }

    handleSearch(event) {
        this.setState({searchValue: event.target.value});
    }
    handleSearchButton() {
        this.setState({showSearch: true});
        const data = {"searchType": this.state.searchType, "searchValue": this.state.searchValue};
        axios.post(COMMODITIES_SEARCH_API, data)
            .then(response => {
                this.setState({commodities: response.data});
            })
    };
    clearButton() {
        this.setState({showSearch: false});
        this.setState({showNameSort: false});
        this.setState({showPriceSort: false});
        CommodityService.getCommodities().then((response) => {
            this.setState({commodities: response.data})
        })
    }
    searchByName() {
        this.setState({searchType: "name"});
    }
    searchByCategory() {
        this.setState({searchType: "category"});
    }
    searchByProvider() {
        this.setState({searchType: "provider"});
    }
    sortByName() {
        this.setState({sortType:"name"})
        this.setState({showNameSort: !this.state.showNameSort})
        this.setState({showPriceSort: false})
        const data = {"commodities": this.state.commodities};
        axios.post(COMMODITIES_SORT_NAME_API, JSON.stringify(data))
            .then(response => {
                this.setState({commodities: response.data});
            })
    }
    sortByPrice() {
        this.setState({sortType:"price"})
        this.setState({showPriceSort: !this.state.showPriceSort})
        this.setState({showNameSort: false})
        const data = {"commodities": this.state.commodities};
        axios.post(COMMODITIES_SORT_PRICE_API, data)
            .then(response => {
                this.setState({commodities: response.data});
            })
    }

    componentDidMount() {
        if (localStorage.getItem('currentUsername') === 'null') {
            this.setState({currentUserNull:true});
        }
        CommodityService.getCommodities().then((response) => {
            this.setState({commodities: response.data})
        })
    }

    render() {
        const currentUsername = localStorage.getItem('currentUsername');

        return (
            <div id="body-wrapper" className="container-fluid p-0 d-flex flex-column flex-nowrap">
                <header>
                    <div id="header-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-1 d-flex flex-row flex-nowrap justify-content-center align-items-center">
                        <div id="header-logo" className="d-flex flex-row flex-nowrap align-items-center">
                            <a href="/" id="baloot-logo"></a>
                            <a href="/" id="baloot-title"
                               className="color-copper ms-2 fw-semibold fs-4">Baloot</a>
                        </div>
                        <div id="search-bar" className="mx-auto d-flex flex-row align-items-center">
                            <input type="text" id="search-text" placeholder="search your product ..."
                                   className="bg-linen color-grey rounded-3" onChange={this.handleSearch} value={this.state.searchValue} />
                                <select id="search-select" className="ps-3 pe-2 bg-dun color-brown rounded-3 border-0">
                                    <option onClick={this.searchByName}> name </option>
                                    <option onClick={this.searchByCategory}> category </option>
                                    <option onClick={this.searchByProvider}> provider </option>
                                </select>
                                <Button onClick={this.handleSearchButton}>search</Button>
                                <Button onClick={this.clearButton}>Clear</Button>
                        </div>
                        <div>
                            {this.state.currentUserNull
                                ?
                                <LoginRegisterCard />
                                :
                                <div id="user-cart">
                                    <div className="btn-toolbar wd-100" role="toolbar">
                                        <a href="/user"
                                           className="btn color-brown col-5 d-flex justify-content-center align-items-center">{currentUsername}</a>
                                        <a href="/user"
                                           className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                            <span className="color-copper me-auto">Cart</span>
                                            <span className="color-copper">0</span>
                                        </a>
                                    </div>
                                </div>
                            }
                        </div>
                    </div>
                </header>
                <main>
                    <div id="main-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-0 mb-4 mb-xl-5 d-flex flex-column flex-nowrap align-items-center">
                        <div id="commodity-filter"
                             className="main-item col-10 bg-linen px-5 py-3 d-flex flex-row flex-nowrap justify-content-center align-items-center rounded-4">
                            <div id="available"
                                 className="form-check form-switch me-auto d-flex flex-row flex-nowrap align-items-center">
                                <label className="form-check-label color-brown fw-semibold fs-5"
                                       htmlFor="flexSwitchCheckDefault">Available Commodities</label>
                                <input className="form-check-input ms-3 mb-1 fs-3" type="checkbox" role="switch"
                                       id="flexSwitchCheckDefault"/>
                            </div>
                            <div id="sort" className="d-flex flex-row flex-nowrap align-items-center">
                                <span className="color-brown me-3">sort by:</span>
                                <div className="btn-toolbar" role="toolbar">
                                    <button onClick={this.sortByName}
                                           className="mx-3 bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center"
                                           >name
                                    </button>
                                    <button onClick={this.sortByPrice}
                                         className="mx-3 bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center"
                                    >price
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div id="commodities" className="main-item col-10">
                            <div className="row row-cols-4 g-4">
                                {
                                    this.state.commodities.map(
                                        commodity =>
                                            <div key={commodity.id} className="col">
                                                <div className="card h-100 px-2 py-3">
                                                    <div className="card-body py-0">
                                                        <div className="card-title color-brown m-0 fw-semibold">
                                                            <div style={{cursor: 'pointer'}}
                                                                onClick={() => this.loadCommodity(commodity.id, commodity.providerId)}>
                                                                {commodity.name}
                                                            </div>
                                                        </div>
                                                        <div
                                                            className="card-text color-red fw-semibold">{commodity.inStock} left
                                                            in stock
                                                        </div>
                                                    </div>
                                                    <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id, commodity.providerId)}>
                                                        <img src={commodity.image} className="card-img-top my-2"
                                                             alt="commodity"/>
                                                    </div>
                                                    <div
                                                        className="card-body py-0 d-flex flex-row flex-nowrap align-items-center">
                                            <span
                                                className="color-brown fw-semibold me-auto">{commodity.price}T</span>
                                                        <AddToCartCard commodityId={commodity.id} inStock={commodity.inStock}/>
                                                    </div>
                                                </div>
                                            </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>
                </main>
                <footer>
                    <div id="footer-wrapper"
                         className="bg-brown color-white px-5 py-2 fw-semibold container-fluid d-flex flex-row flex-nowrap justify-content-center align-items-center">
                        <div className="color-linen fs-5">2023 © VPT</div>
                    </div>
                </footer>
            </div>
        )
    }
}

export default CommodityComponent