package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapters.MovieRecyclerAdapter
import com.example.movieapp.adapters.OnMovieListener
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.models.MovieModel
import com.example.movieapp.utils.Credentials

import com.example.movieapp.utils.GenreIdService
import com.example.movieapp.view_models.MainActivityViewModel


class MainActivity : AppCompatActivity(), OnMovieListener {
    //View Model
    lateinit var movieListViewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var movieAdapter: MovieRecyclerAdapter

    var isPopular: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieListViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        initToolbar()
        initVars()
        observeAnyChange()
        setupSearchView()
        addRecyclerViewOnScrollListener()

        observePopularMovies()
        movieListViewModel.searchPopularMovies(1);
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observePopularMovies() {
        movieListViewModel.getPopularMovies().observe(this, {
            //observing data change
            if (it != null) {
                movieAdapter.submitList(it)
            }
        })
    }

    private fun addRecyclerViewOnScrollListener() {
        binding.recyclerViewMain.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    movieListViewModel.searchMovieNextPage()
                }
            }
        })
    }

    private fun initToolbar() {
        val toolBar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolBar)
    }

    private fun setupSearchView() {
        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Credentials.POPULAR = false
                    isPopular = false
                    searchMovieByName(query, 1)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initVars() {
        //initializing genre id
        GenreIdService.init()
        movieAdapter = MovieRecyclerAdapter(this)
        binding.recyclerViewMain.adapter = movieAdapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun searchMovieByName(query: String, pageNumber: Int) {
        movieListViewModel.searchMovieByName(query, pageNumber)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeAnyChange() {
        movieListViewModel.getMovies().observe(this, {
            //observing data change
            if (it != null) {
                movieAdapter.submitList(it)
            }
        })
    }

    override fun onMovieClick(position: Int) {
        var movieModel: MovieModel? = null

        movieModel = if (isPopular) {
            movieListViewModel.getPopularMovies().value?.get(position)
        } else {
            movieListViewModel.getMovies().value?.get(position)
        }

        val intent = Intent(this, MovieDetails::class.java)
        intent.putExtra("movie", movieModel)
        startActivity(intent)
    }

    override fun onCategoryClick(category: String) {
        val intent = Intent(this, MoviesByCategory::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}