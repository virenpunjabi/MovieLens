import React, { Component } from 'react';
import './home.css';
import MovieTiles from './movieTiles'


// components

// header
class Header extends React.Component {
  render() {
    return (
          <div className="top-row">
              <a className="logo" id="logo" href="">MovieLens</a>
              <form className="movie-search-form" id="movie-search-form" action="">
                  <input type="text" className="movie-search-field" id="movie-search-field"/>
                  <input type="submit" value="Search" className="movie-search-btn" id="movie-search-btn"/>
              </form>
          </div>
    );
  }
}


// Sidebar
class SideBar extends React.Component {
    render() {
        return (
            <div className="siderBar-filters" id="siderBar-filters" >
                <div>
                    <h1 style={{display: 'inline-block', margin: '5%'}}>Filters</h1>
                    <span style={{float: 'right', margin: '8%'}}>
                        <input type="button" value="Apply" style={{padding: '10%', fontSize: '110%'}} />
                    </span>
                </div>

                <div className="language-filter-box" id="language-filter-box" >
                    <div className="language-top-row" id="language-top-row">
                        <span style={{marginLeft: '5%'}}>Language</span>
                        <span style={{float: 'right', marginRight: '5%'}}>Clear</span>
                    </div>
                    <div className="language-filters" id="language-filters" >
                        <span class="filter-option">Hindi</span>
                        <span class="filter-option">English</span>
                        <span class="filter-option">Marathi</span>
                        <span class="filter-option">Gujrati</span>
                        <span class="filter-option">Telugu</span>
                        <span class="filter-option">Kannada</span>
                        <span class="filter-option">Tamil</span>
                    </div>
                </div>

                <div className="language-filter-box" id="language-filter-box" >
                    <div className="language-top-row" id="language-top-row">
                        <span style={{marginLeft: '5%'}}>Genre</span>
                        <span style={{float: 'right', marginRight: '5%'}}>Clear</span>
                    </div>
                    <div className="language-filters" id="language-filters" >
                        <span class="filter-option">Romantic</span>
                        <span class="filter-option">Comedy</span>
                        <span class="filter-option">Drama</span>
                        <span class="filter-option">Action</span>
                        <span class="filter-option">Horror</span>
                        <span class="filter-option">Adventure</span>
                        <span class="filter-option">Thriller</span>
                        <span class="filter-option">Biography</span>
                        <span class="filter-option">Fantasy</span>
                        <span class="filter-option">Sci-Fi</span>
                    </div> 
                </div> 
            </div>
        );
    }
}

// Result
class Result extends React.Component {
    state = {
        movies: []
    } 

    componentDidMount() {
        // var backendURL = "https://localhost:8443/home";
        var backendURL = "https://20.193.31.25/home";

        fetch(backendURL)
        .then( res => res.json())
        .then( data => {
            this.setState({movies: data})
        })
        .catch(console.log);
    }

    render() {
        return (
            <div class="clearfix" id="movieTileWrapper">
                <MovieTiles movies = {this.state.movies} />
            </div>
        );
    }
}


// Body
class Body extends React.Component {
    render() {
        return (
            <div id="main-body" class="clearfix">
                <SideBar />
                <Result />
            </div>
        );
    }
}


// Home Page
class Home extends React.Component {
    render() {
        return (
            <div classname="home">
                <Header />
                <Body />
            </div>
        );
    }
}

export default Home;