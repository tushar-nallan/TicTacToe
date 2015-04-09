package com.tramsun.tictactoe.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tramsun.tictactoe.Constants;
import com.tramsun.tictactoe.R;
import com.tramsun.tictactoe.adapters.GameAdapter;
import com.tramsun.tictactoe.interfaces.GameRestartable;
import com.tramsun.tictactoe.controllers.GameData;
import com.tramsun.tictactoe.views.SquareGridView;

/**
 * Created by Tushar on 19-03-2015.
 */
public class GameFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Context context;
    private int gridLength;
    private GameData gameData;
    private SquareGridView gridView;
    private GameAdapter adapter;
    private TextView turnText;
    private int maxPlayers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            gridLength = args.getInt(Constants.GRID_SIZE, Constants.DEFAULT_GRID_SIZE);
            maxPlayers = args.getInt(Constants.MAX_PLAYERS_KEY, Constants.DEFAULT_MAX_PLAYERS);
            gameData = new GameData(gridLength, maxPlayers);
        }
    }

    public static Fragment newInstance(int n, int maxPlayers) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.MAX_PLAYERS_KEY, maxPlayers);
        args.putInt(Constants.GRID_SIZE, n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = inflater.getContext();
        View root = inflater.inflate(R.layout.game_layout, container, false);
        gridView = (SquareGridView) root.findViewById(R.id.game_grid);
        adapter = new GameAdapter(context, gameData);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(gridLength);
        gridView.setOnItemClickListener(this);
        turnText = (TextView) root.findViewById(R.id.player_turn);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        gridView.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)gridView.getLayoutParams();
                if(lp == null)
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Rect r= new Rect();
                gridView.getGlobalVisibleRect(r);
                lp.height = r.width();
                Log.e("GameFragment", "width="+r.width());
                gridView.setLayoutParams(lp);
                gridView.setMinimumHeight(r.width());
            }
        });
    }

    public void showGameOverDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                        //do things
                    }
                }).setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(getActivity() instanceof GameRestartable) {
                            ((GameRestartable)getActivity()).onGameRestart();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(gameData.isGridFull()) {
            Toast.makeText(context, "Game already over", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean playerWon = gameData.play(position);
        adapter.notifyDataSetChanged();
        if(playerWon) {
            showGameOverDialog("Player "+gameData.getActivePlayer()+" won!");
        } else if(gameData.isGridFull()) {
            showGameOverDialog("It's a TIE!");
        } else {
            turnText.setText("Player "+gameData.getActivePlayer()+"'s Turn");
        }
    }
}
