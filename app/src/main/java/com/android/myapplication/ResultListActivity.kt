package com.android.myapplication;

import android.R
import android.R.attr.country
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


public class ResultListActivity:AppCompatActivity() {

    private var mViewModel: MyViewModel? = null
    private var resultsList:ArrayList<Results>? = null
    private var mRecyclerView:RecyclerView? = null
    private var mSpinner:Spinner? = null
    // Spinner Drop down elements
    val mCategories: MutableList<String> = ArrayList()
    private var mSearchArtistName:String? = ""
    private var mTrackName:String? = ""
    private var mCollectionName:String? = ""
    private var mCollectionPrice:String? =""
    private var mReleaseDate:String? = ""
    private lateinit  var mIdEmptyTv:TextView
    private lateinit var mSearchIv:ImageView;
    private lateinit var  mSpinnerItems: ArrayList<String>;





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSearchArtistName       =   intent.getStringExtra("ArtistName")
        mTrackName              =   intent.getStringExtra("TrackName")
        mCollectionName         =   intent.getStringExtra("CollectionName");

        mCollectionPrice        =   intent.getStringExtra("CollectionPrice");
        mReleaseDate            =   intent.getStringExtra("ReleaseDate");

        mSpinnerItems           =   ArrayList<String>()
        mSpinnerItems.add("Collection Name");
        mSpinnerItems.add("track name");
        mSpinnerItems.add("artist name");
        mSpinnerItems.add("collection price Descending");

        setContentView(com.android.myapplication.R.layout.result_list_activity)

        mRecyclerView           =   findViewById(com.android.myapplication.R.id.id_rv)
        mIdEmptyTv              =   findViewById(com.android.myapplication.R.id.id_empty_tv);

        mSearchIv               =   findViewById(com.android.myapplication.R.id.id_search_icon_iv);

        mSpinner                =   findViewById(com.android.myapplication.R.id.id_spinner);


        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, com.android.myapplication.R.layout.my_spinner_layout, mSpinnerItems)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        mSpinner?.adapter = arrayAdapter

        mSearchIv.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ResultListActivity, AlbumSearchActivity::class.java)
            startActivityForResult(intent, 1000)
        })

        mRecyclerView?.layoutManager = LinearLayoutManager(this.applicationContext);

        resultsList = ArrayList<Results>();

        mViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        mViewModel!!.init(applicationContext,this)
        mViewModel!!.resultsLiveData.observe(this, Observer<ArrayList<Results>>() {


            var matchingResults: ArrayList<Results> = ArrayList()
            if (it != null) {

                for (i in 0..it.size - 1) {
                    var results: Results = it.get(i)

                    try {
                        var collectionName: String? = results.collectionName;
                    } catch (e: Exception) {
                        Log.e("", "");
                    }

                    try {
                        var collectionPrice: String? = results.collectionPrice;
                    } catch (e: Exception) {
                        Log.e("", "");
                    }
                    try {

                        var trackName: String? = results.trackName;
                    } catch (e: Exception) {
                        Log.e("", "");
                    }

                    try {
                        var releaseDate: String? = results.releaseDate;
                    } catch (e: Exception) {
                        Log.e("", "");
                    }


//                    if (doesCollectionNameMatch(collectionName)) {
//                            if (doesCollectionPriceMatch(collectionPrice)) {
//                                if (doesTrackNameMatch(trackName)) {
//                                    if (doesReleaseDateMatch(releaseDate)) {
//                                        matchingResults.add(results)
//                                    }
//                                }
//                            }
//                        }

                }
                var resultsAdapter: ResultsAdapter = ResultsAdapter();
                resultsAdapter.setResults(it);
//                if(matchingResults.size == 0){
//                    //No items found..
//                    //Show empty message...
//                    mIdEmptyTv.setText("Search Result Is Empty")
//
//                }

                mRecyclerView?.adapter = resultsAdapter
                mRecyclerView?.invalidate()
                mRecyclerView?.adapter?.notifyDataSetChanged()
            }
        })
    }

    fun performSearch() {
        mViewModel?.fetchApiResponse()
    }

    fun doesCollectionNameMatch(collectionName: String):Boolean{
        if(collectionName==null){
            return false;
        }
        if(mCollectionName.equals("")){
            return false;
        }

        if(mCollectionName.equals(collectionName)){
            return true;
        }

        return false;
    }

    fun doesCollectionPriceMatch(collectionPrice: String):Boolean{
        if(collectionPrice == null){
            return false;
        }
        if(mCollectionPrice.equals("")){
            return false;
        }

        if(mCollectionPrice.equals(collectionPrice)){
            return true;
        }

        return false;
    }

    fun doesTrackNameMatch(trackName: String):Boolean{
        if(trackName==null){
            return false;
        }
        if(mTrackName.equals("")){
            return false;
        }

        if(mTrackName.equals(trackName)){
            return true;
        }

        return false;
    }

    fun doesReleaseDateMatch(releaseDate: String):Boolean{
        if(releaseDate==null){
            return false;
        }
        if(mReleaseDate.equals("")){
            return false;
        }

        if(mReleaseDate.equals(releaseDate)){
            return true;
        }

        return false;
    }
}