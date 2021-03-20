import React from 'react';
import ReactDOM from 'react-dom';
import './style.css';


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
    render() {
        return (
            <div className="language-filters" id="language-filters">
                <span className="filter-option" style={{width: '16%'}}>
                    <img src="https://storageaccmovielens.blob.core.windows.net/images/chhichore.jpg" alt="" width="150" height="213"/>
                    <label class="movie-title">Chhichore</label>
                    <label class="movie-attr">4.5</label>
                    <label class="list-attr">DRAMA,COMEDY</label>
                    <label class="list-attr">HINDI</label>
                </span>
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


// ========================================

ReactDOM.render(
  <Home />,
  document.getElementById('root')
);
