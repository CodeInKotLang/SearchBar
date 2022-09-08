package com.synac.searchbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.synac.searchbar.ui.theme.SearchBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchBarTheme {
                val viewModel = viewModel<FamousActorsViewModel>()
                FamousActorsScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

val actorsList = listOf(
    "Leonardo DiCaprio",
    "Chris Evans",
    "Brad Pitt",
    "Elizabeth Olsen",
    "Kate Winslet",
    "Tom Holland",
    "Joseph Gordon-Levitt",
    "Scarlett Johansson",
    "Anne Hathaway",
    "Meryl Streep",
    "Tom Hardy",
    "Tom Cruise",
    "Sandra Bullock",
    "Charlize Theron",
    "Dakota Johnson",
    "James Franco",
    "Paul Rudd",
    "Jennifer Lawrence",
    "Benedict Cumberbatch",
    "Hugh Jackman",
    "Tom Hiddleston",
    "Anna Kendrick",
    "Daniel Craig",
    "Shah Rukh Khan",
    "Will Smith",
    "George Clooney",
    "Liam Neeson",
    "Angelina Jolie",
    "Michael Fassbender",
    "Idris Elba",
    "Russell Crowe",
    "Ryan Gosling",
    "Ben Affleck",
    "Chris Hemsworth",
    "Margot Robbie",
    "Emma Stone",
    "Natalie Portman",
    "Tom Hanks",
    "Denzel Washington",
    "Mark Wahlberg",
    "Matt Damon",
    "Chris Pratt",
    "Samuel L. Jackson",
    "Johnny Depp",
    "Robert Downey Jr",
    "Christian Bale",
    "Matthew McConaughey",
    "Morgan Freeman",
    "Jake Gyllenhaal",
    "Jeremy Renner",
    "Dwayne Johnson",
    "Michael B. Jordan",
    "Mark Ruffalo",
    "Jesse Eisenberg",
    "Keanu Reeves",
)