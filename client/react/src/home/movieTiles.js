import React from 'react';
import './movieTile.css'

// Movie Tile
const MovieTiles = ({ movies }) => {
    return (
        <div id="movie-container" class="clearfix" className="movie-container">
            {
                movies.map(movie => (
                    <span className="filter-option" style={{width: '16%', maxHeight:'50%'}}>
                        <img src={movie.posterUri} alt="" width="150" height="213"/>
                        <label className="movie-title">{movie.name}</label>
                        <label className="movie-attr">{movie.rating}</label>
                        <label className="list-attr">{movie.genres}</label>
                        <label className="list-attr">{movie.languages}</label>
                    </span>
                ))
            }
        </div>    
    )
};

export default MovieTiles;
