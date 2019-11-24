package ru.sample.movies.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sample.movies.R
import ru.sample.movies.domain.entity.Movie
import javax.inject.Inject

/**
 * [MoviePagedListAdapter] is responsible for presenting movie data from PagedList in a RecyclerView.
 * The PagedListAdapter is notified when pages are loaded, and it uses DiffUtil to compute fine grain
 * updates as new data is received.
 */
class MoviePagedListAdapter @Inject constructor (private val picasso: Picasso) : PagedListAdapter<Movie, MoviePagedListAdapter.MoviePagedViewHolder>(DIFF_CALLBACK) {

    /** An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    var mOnClickHandler: MoviePagedListAdapterOnClickHandler? = null

    /**
     * The interface that receives onClick messages.
     */
    interface MoviePagedListAdapterOnClickHandler {
        fun onItemClick(movie: Movie)
    }


    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     * can use this viewType integer to provide a different layout.
     * @return A new MoviePagedViewHolder that holds the MovieListItemBinding
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mMovieItemBinding = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
        return MoviePagedViewHolder(mMovieItemBinding)
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: MoviePagedViewHolder, position: Int) {

        val movie = getItem(position)

        // Load thumbnail with Picasso library
        picasso.load(movie?.poster_path)
            .error(R.drawable.image)
            .into(holder.imageMovie)

        // Display the title
        holder.tvTitle.text = movie?.title
        holder.itemView.setOnClickListener {
            if(movie != null)
                mOnClickHandler?.onItemClick(movie)
        }
    }

    /**
     * Cache of the children views for a list item.
     */
    class MoviePagedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageMovie = itemView.findViewById(R.id.iv_thumbnail) as ImageView
        val tvTitle = itemView.findViewById(R.id.tv_title) as TextView

    }

    companion object {
        /**
         * Tell MoviePagedListAdapter how to compute the differences between the two elements
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            // The ID property identifies when items are the same
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}
