package rasppi.intelligenthouse.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import rasppi.intelligenthouse.MainActivity;
import rasppi.intelligenthouse.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentController.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentController extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "roomNumber";
    private static final String ARG_PARAM2 = "tempValue";
    private static final String ARG_PARAM3 = "humValue";
    private static final String ARG_PARAM4 = "lightState";
    private static final String ARG_PARAM5 = "fireState";
    private static final String ARG_PARAM6 = "motionState";

    private Bundle savedState = null;
    // TODO: Rename and change types of parameters
    private int roomNumber;
    private float tempVal;
    private float humVal;
    private float lightState;
    private float fireState;
    private float motionState;
    private float bulbState;

    TextView tempValue;
    TextView humValue;
    ImageView lightIcon;
    ImageView fireIcon;
    ImageView motionIcon;
    ImageButton bulbButton;
    ImageButton blindOnButton;
    ImageButton blindOffButton;

    View rootView;
    private MainActivity mainActivity;

    private OnFragmentInteractionListener mListener;

    public FragmentController() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment room1.
     */

    public static FragmentController newInstance(int roomNumber, float tempVal, float humVal, float lstate, float fstate, float mstate) {
        FragmentController fragment = new FragmentController();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1,roomNumber);
        args.putFloat(ARG_PARAM2, tempVal);
        args.putFloat(ARG_PARAM3, humVal);
        args.putFloat(ARG_PARAM4,lstate);
        args.putFloat(ARG_PARAM5,fstate);
        args.putFloat(ARG_PARAM6,mstate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomNumber = getArguments().getInt(ARG_PARAM1);
            tempVal = getArguments().getFloat(ARG_PARAM2);
            humVal = getArguments().getFloat(ARG_PARAM3);
            lightState = getArguments().getFloat(ARG_PARAM4);
            fireState = getArguments().getFloat(ARG_PARAM5);
            motionState = getArguments().getFloat(ARG_PARAM6);
            bulbState = 0;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //saveInstanceState
        if(rootView!=null){
            if((ViewGroup)rootView.getParent()!=null)
                ((ViewGroup)rootView.getParent()).removeView(rootView);
            //return rootView;
        }

        // Inflate the layout for this fragment
        /*
        switch (roomNumber) {
            case 0: rootView = inflater.inflate(R.layout.room_fragment_1, container, false);
                break;
            case 1: rootView = inflater.inflate(R.layout.room_fragment_1, container, false);
                break;
            case 2: rootView = inflater.inflate(R.layout.room_fragment_3, container, false);
                break;
            case 3: rootView = inflater.inflate(R.layout.room_fragment_4, container, false);
                break;
            case 4: rootView = inflater.inflate(R.layout.room_fragment_5, container, false);
                break;
            case 5: rootView = inflater.inflate(R.layout.room_fragment_6, container, false);
                break;
            default: rootView = inflater.inflate(R.layout.room_fragment_1, container, false);
        }
        */
        mainActivity = (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.room_fragment_1, container, false);
        setUIVievs();
        bulbButton = (ImageButton) rootView.findViewById(R.id.bulbButton);
        bulbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bulbSetAction(view);
            }
        });
        blindOffButton = (ImageButton) rootView.findViewById(R.id.blindOffButton);
        blindOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blindSetOffAction(view);
            }
        });
        blindOnButton = (ImageButton) rootView.findViewById(R.id.blindOnButton);
        blindOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blindSetOnAction(view);
            }
        });
        return rootView;
    }

    private void setUIVievs(){

        tempValue = (TextView) rootView.findViewById(R.id.tempValue);
        tempValue.setText(String.format("%.1f °C", tempVal));
        humValue = (TextView) rootView.findViewById(R.id.humValue);
        humValue.setText(String.format("%.0f%%", humVal));

        lightIcon = (ImageView) rootView.findViewById(R.id.lightIcon);
        if(lightState == 1) lightIcon.setVisibility(View.VISIBLE);
        else lightIcon.setVisibility(View.INVISIBLE);

        fireIcon = (ImageView) rootView.findViewById(R.id.fireIcon);
        if(fireState == 1) fireIcon.setVisibility(View.VISIBLE);
        else fireIcon.setVisibility(View.INVISIBLE);

        motionIcon = (ImageView) rootView.findViewById(R.id.motionIcon);
        if(motionState == 1) motionIcon.setVisibility(View.VISIBLE);
        else motionIcon.setVisibility(View.INVISIBLE);
    }



/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setTemp(float temp) {
        if((ViewGroup)rootView.getParent()!=null) {
            TextView text = (TextView) getView().findViewById(R.id.tempValue);
            text.setText(String.format("%.1f °C", temp));
        } else {
            tempVal = temp;
        }
    }

    public void setHum(float hum) {
        if((ViewGroup)rootView.getParent()!=null) {
            TextView text = (TextView) getView().findViewById(R.id.humValue);
            text.setText(String.format("%.0f%%", hum));
        } else {
            humVal = hum;
        }
    }

    public void setLight(float state) {
        if((ViewGroup)rootView.getParent()!=null) {
            ImageView image = (ImageView) getView().findViewById(R.id.lightIcon);
            if (state == 1)
                image.setVisibility(View.VISIBLE);
            else
                image.setVisibility(View.INVISIBLE);
        } else {
            lightState = state;
        }
    }

    public void setFire(float state) {
        if((ViewGroup)rootView.getParent()!=null) {
            ImageView image = (ImageView) getView().findViewById(R.id.fireIcon);
            if (state == 1)
                image.setVisibility(View.VISIBLE);
            else
                image.setVisibility(View.INVISIBLE);
        } else {
            fireState = state;
        }
    }

    public void setMotion(float state) {
        if((ViewGroup)rootView.getParent()!=null) {
            ImageView image = (ImageView) getView().findViewById(R.id.motionIcon);
            if (state == 1)
                image.setVisibility(View.VISIBLE);
            else
                image.setVisibility(View.INVISIBLE);
        } else {
            motionState = state;
        }
    }


    public float bulbSetAction(View view) {
        if(mainActivity.getUserInfo().getPrivileges()[0] == 1) {
            ImageButton button = (ImageButton) view.findViewById(R.id.bulbButton);
            if (bulbState == 1) {
                button.setImageResource(R.drawable.bulboff);
                bulbState = 0;
                mListener.bulbSetAction(bulbState, roomNumber + 1);
                return bulbState;
            } else {
                button.setImageResource(R.drawable.bulbon);
                bulbState = 1;
                mListener.bulbSetAction(bulbState, roomNumber + 1);
                return bulbState;
            }
        } else {
            CharSequence text;
            text = "You have too low privileges to do it!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mainActivity, text, duration);
            toast.show();
            return 0;
        }
    }

    public float blindSetOnAction(View view) {
        if(mainActivity.getUserInfo().getPrivileges()[0] == 1) {
                mListener.blindSetOnAction(1, roomNumber + 1);
                return bulbState;
        } else {
            CharSequence text;
            text = "You have too low privileges to do it!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mainActivity, text, duration);
            toast.show();
            return 0;
        }
    }

    public float blindSetOffAction(View view) {
        if(mainActivity.getUserInfo().getPrivileges()[0] == 1) {
            mListener.blindSetOffAction(0, roomNumber + 1);
            return bulbState;
        } else {
            CharSequence text;
            text = "You have too low privileges to do it!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mainActivity, text, duration);
            toast.show();
            return 0;
        }
    }



    public int getRoomNumber(){
        return  roomNumber;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
*/
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void bulbSetAction(float value, int deviceId);
        void blindSetOnAction(float value, int deviceId);
        void blindSetOffAction(float value, int deviceId);
    }

}
