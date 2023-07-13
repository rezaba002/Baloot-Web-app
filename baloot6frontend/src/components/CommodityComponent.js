import React from "react";
import axios from "axios";
import {Button} from "@mui/material";

import CommodityService from "../services/CommodityService";
import UserService from "../services/UserService";

import AddToCartCard from "../cards/AddToCartCard";
import LoginRegisterCard from "../cards/LoginRegisterCard";

const COMMODITIES_SEARCH_API = "http://localhost:8080/searchCommodity"
const COMMODITIES_SORT_NAME_API = "http://localhost:8080/sortNameCommodity"
const COMMODITIES_SORT_PRICE_API = "http://localhost:8080/sortPriceCommodity"

class CommodityComponent extends React.Component {
    constructor(props) {
        super(props);

        this.handleSearchButton = this.handleSearchButton.bind(this);
        this.handleSearchType = this.handleSearchType.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.sortByName = this.sortByName.bind(this);
        this.sortByPrice = this.sortByPrice.bind(this);
        this.clearButton = this.clearButton.bind(this);
        this.handleCartNumber = this.handleCartNumber.bind(this);

        this.state = {
            numberOfBuyListItems: 0,
            currentUserNull: false,
            commodities: [],
            commoditiesTemp: [],
            showSearch: false,
            searchType: "name",
            searchValue: "",
            showNameSort: false,
            showPriceSort: false,
            sortType: "",

            currentPage: 1,
            commoditiesPerPage: 12,
        }
    }

    getPaginatedCommodities() {
        const { currentPage, commoditiesPerPage, commodities } = this.state;
        const indexOfLastCommodity = currentPage * commoditiesPerPage;
        const indexOfFirstCommodity = indexOfLastCommodity - commoditiesPerPage;
        return commodities.slice(indexOfFirstCommodity, indexOfLastCommodity);
    }

    getPaginatedCommoditiesTemp() {
        const { currentPage, commoditiesPerPage, commoditiesTemp } = this.state;
        const indexOfLastCommodity = currentPage * commoditiesPerPage;
        const indexOfFirstCommodity = indexOfLastCommodity - commoditiesPerPage;
        return commoditiesTemp.slice(indexOfFirstCommodity, indexOfLastCommodity);
    }

    goToPreviousPage() {
        this.setState((prevState) => ({
            currentPage: (prevState.currentPage !== 1) ? prevState.currentPage - 1 : prevState.currentPage
        }));
    }

    goToNextPage() {
        this.setState((prevState) => ({
            currentPage: (prevState.currentPage !== 4) ? prevState.currentPage + 1 : prevState.currentPage
        }));
    }

    goToPage(pageNumber) {
        this.setState({
            currentPage: pageNumber
        });
    }

    handleKeyPress(event) {
        if (event.key === 'Enter') {
            this.handleSearchButton();
        }
    }

    handleSearch(event) {
        this.setState({searchValue: event.target.value});
    }

    handleSearchButton() {
        this.setState({showSearch: true});
        this.setState({showNameSort: false});
        this.setState({showPriceSort: false});
        this.setState({currentPage: 1});
        console.log(this.state.searchType);
        const data = {"searchType": this.state.searchType, "searchValue": this.state.searchValue};
        axios.post(COMMODITIES_SEARCH_API, data)
            .then(response => {
                this.setState({commodities: response.data});
                if (response.status === 400)
                    window.location.replace("https://localhost:3000/badrequest");
                if (response.status === 403)
                    window.location.replace("https://localhost:3000/forbidden");
            })
    }

    clearButton() {
        this.setState({showSearch: false});
        this.setState({showNameSort: false});
        this.setState({showPriceSort: false});
        this.setState({searchValue: ""})
        this.setState({currentPage: 1});
        CommodityService.getCommodities().then((response) => {
            this.setState({commodities: response.data})
        })
    }

    handleSearchType(event) {
        this.setState({searchType : event.target.value});
    }

    sortByName() {
        this.setState({sortType:"name"})
        this.setState({showNameSort: !this.state.showNameSort})
        this.setState({showPriceSort: false})
        this.setState({currentPage: 1});
        const data = {"commodities": this.state.commodities}
        axios.post(COMMODITIES_SORT_NAME_API, JSON.stringify(data))
            .then(response => {
                this.setState({commoditiesTemp: response.data});
            })
    }

    sortByPrice() {
        this.setState({sortType:"price"})
        this.setState({showPriceSort: !this.state.showPriceSort})
        this.setState({showNameSort: false})
        this.setState({currentPage: 1})
        const data = {"commodities": this.state.commodities};
        axios.post(COMMODITIES_SORT_PRICE_API, data)
            .then(response => {
                this.setState({commoditiesTemp: response.data});
            })

    }

    loadCommodity(commodityId) {
        let path = "/commodities/" + commodityId;

        setTimeout(() => {
            window.location.replace("http://localhost:3000" + path);
        }, 88);
    }

    handleCartNumber() {
        UserService.getUserBuyList().then((response) => {
            this.setState({numberOfBuyListItems: response.data.length})
        })
    }

    componentDidMount() {
        setTimeout(() => {
            if (localStorage.getItem('currentUsername') === 'undefined') {
                this.setState({currentUserNull: true});
            }
        },88)
        CommodityService.getCommodities().then((response) => {
            this.setState({commodities: response.data})
        })
        this.handleCartNumber();
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
                            <input onKeyDown={this.handleKeyPress.bind(this)} type="text" id="search-text" placeholder="search your product ..."
                               className="bg-linen color-grey rounded-3" onChange={this.handleSearch} value={this.state.searchValue} />
                            <i id="search-text-button" onClick={this.handleSearchButton}></i>
                            <select id="search-select" className="ps-3 pe-2 bg-dun color-brown rounded-3 border-0"
                                    onChange={this.handleSearchType} value={this.state.searchType}>
                                <option value="name"> name </option>
                                <option value="category"> category </option>
                                <option value="provider"> provider </option>
                            </select>
                            <a id="clearSearch" className="btn bg-linen color-copper ms-3 rounded-3 d-flex justify-content-center align-items-center" onClick={this.clearButton}>Clear</a>
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
                                        {this.state.numberOfBuyListItems === 0 ?
                                            <a href="/user"
                                               className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                                <span className="color-copper me-auto">Cart</span>
                                                <span className="color-copper">{this.state.numberOfBuyListItems}</span>
                                            </a>
                                            :
                                            <a href="/user"
                                               className="btn bg-brown color-linen col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                                <span className="color-linen me-auto">Cart</span>
                                                <span className="color-linen">{this.state.numberOfBuyListItems}</span>
                                            </a>
                                        }
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
                                <div className="d-flex flex-row flex-nowrap align-items-center">
                                    {!this.state.showNameSort ?
                                        <button onClick={this.sortByName}
                                                className="notfocused btn bg-linen color-grey col-5 mx-2 rounded-3 d-flex justify-content-center align-items-center"
                                        >name
                                        </button>
                                        :
                                        <button onClick={this.sortByName}
                                                className="focused btn bg-linen color-copper col-5 mx-2 rounded-3 d-flex justify-content-center align-items-center"
                                        >name
                                        </button>
                                    }
                                    {!this.state.showPriceSort ?
                                        <button onClick={this.sortByPrice}
                                                className="notfocused btn bg-linen color-grey col-5 mx-2 rounded-3 d-flex justify-content-center align-items-center"
                                        >price
                                        </button>
                                        :
                                        <button onClick={this.sortByPrice}
                                                className="focused btn bg-linen color-copper col-5 mx-2 rounded-3 d-flex justify-content-center align-items-center"
                                        >price
                                        </button>
                                    }
                                </div>
                            </div>
                        </div>
                        <div id="commodities" className="main-item col-10">
                            <div className="row row-cols-4 g-4">
                                {!this.state.showNameSort && !this.state.showPriceSort ?
                                    this.getPaginatedCommodities().map(
                                        commodity =>
                                            <div key={commodity.id} className="col">
                                                <div className="card h-100 px-2 py-3">
                                                    <div className="card-body py-0">
                                                        <div className="card-title color-brown m-0 fw-semibold">
                                                            <div style={{cursor: 'pointer'}}
                                                                onClick={() => this.loadCommodity(commodity.id)}>
                                                                {commodity.name}
                                                            </div>
                                                        </div>
                                                        <div
                                                            className="card-text color-red fw-semibold">{commodity.inStock} left
                                                            in stock
                                                        </div>
                                                    </div>
                                                    <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id)}>
                                                        <img src={commodity.image} className="card-img-top my-2"
                                                             alt="commodity"/>
                                                    </div>
                                                    <div
                                                        className="card-body py-0 d-flex flex-row flex-nowrap align-items-center">
                                            <span
                                                className="col-3 d-flex color-brown fw-semibold me-auto">{commodity.price}T</span>
                                                        {!this.state.currentUserNull ?
                                                        <AddToCartCard commodityId={commodity.id} inStock={commodity.inStock} handleCartNumber={this.handleCartNumber}/>
                                                        : null}
                                                    </div>
                                                </div>
                                            </div>
                                    )
                                    :
                                    this.getPaginatedCommoditiesTemp().map(
                                        commodity =>
                                            <div key={commodity.id} className="col">
                                                <div className="card h-100 px-2 py-3">
                                                    <div className="card-body py-0">
                                                        <div className="card-title color-brown m-0 fw-semibold">
                                                            <div style={{cursor: 'pointer'}}
                                                                 onClick={() => this.loadCommodity(commodity.id)}>
                                                                {commodity.name}
                                                            </div>
                                                        </div>
                                                        <div
                                                            className="card-text color-red fw-semibold">{commodity.inStock} left
                                                            in stock
                                                        </div>
                                                    </div>
                                                    <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id)}>
                                                        <img src={commodity.image} className="card-img-top my-2"
                                                             alt="commodity"/>
                                                    </div>
                                                    <div
                                                        className="card-body py-0 d-flex flex-row flex-nowrap align-items-center">
                                            <span
                                                className="color-brown fw-semibold me-auto">{commodity.price}T</span>
                                                        {!this.state.currentUserNull ?
                                                            <AddToCartCard commodityId={commodity.id} inStock={commodity.inStock}/>
                                                        : null}
                                                    </div>
                                                </div>
                                            </div>
                                    )
                                }
                            </div>
                        </div>
                        <div>
                            <button style={{cursor: 'pointer'}} onClick={this.goToPreviousPage.bind(this)}>&lt;</button>
                            <button style={{cursor: 'pointer'}} onClick={this.goToNextPage.bind(this)}>&gt;</button>
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