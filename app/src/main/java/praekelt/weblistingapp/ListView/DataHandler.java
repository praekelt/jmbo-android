//package praekelt.weblistingapp.ListView;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.app.Fragment;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import praekelt.weblistingapp.Utils.Constants;
//import praekelt.weblistingapp.Utils.JSONFilesIO;
//import praekelt.weblistingapp.Utils.JSONValues;
//import praekelt.weblistingapp.Utils.StringUtils;
//
///**
// * Created by altus on 2015/03/17.
// * Holds the Socket fragment, parses messages and sends list data to IndexList
// */
//public class DataHandler extends Fragment {
//    private int lastPub = 0;
//    String lastData = "";
//
//
//    public storeCallbacks callback;
//
//    private encapsulatedData encapData;
//
//
//    public String externalDir;
//    private ExecutorService executorService;
//
//    /**
//     *
//     * @param activity
//     */
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        executorService= Executors.newSingleThreadExecutor();
//
//        setExternalDir((getActivity().getApplicationContext().getExternalFilesDir(null)).toString());
//
//        try {
//            callback = (storeCallbacks) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
//        }
//    }
//
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    /**
//     * Commits the websocket-cient fragment
//     * @param savedInstanceState
//     */
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        encapData = new encapsulatedData();
//
//        lastPub = checkCache();
//    }
//
//    /**
//     * Deletes old notifications
//     */
//    public void onResume() {
//        super.onResume();
//        callback.postNotification(1, "remove", "delete");
//    }
//
//    public void onPause() {
//        super.onPause();
//        try {
//            writeToFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void getList() {}
//
//    /**
//     * callback method called by socket when a message is received
//     * @param data
////     */
//    public void incomingContent(String data) {
//        Log.i("incomingContent: ", "Called");
//
//        if((data.equals(""))  || (lastData.equals(data))) {
//           return;
//        }
//        try {
//            queueArrays(data);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.i("Message Received: ", data);
//    }
//
//    public interface storeCallbacks {
//        public void setData(List<ModelBase> data);
//        public void postNotification(int colour, String text, String operation);
//    }
//
//    public List<ModelBase> getData() {
//        return encapData.tempData;
//    }
//
//    /**
//     * used to add a notification to the list data
//     * @param notification
//     * @throws JSONException
//     */
//    public void addNotification(JSONObject notification) throws JSONException {
//        Log.i("Post", "Notification");
//        executorService.submit(new parseMessages(notification.toString()));
//    }
//
//    /**
//     * set external directory for use when saving cached files to phone
//     * @param dir
//     */
//    public void setExternalDir(String dir) {
//        this.externalDir = dir;
//        File images = new File(externalDir + "/images");
//        if(!images.exists()) {
//            images.mkdir();
//            Log.i("dirMade", "images");
//        }
//        File feed = new File(externalDir + "/feed");
//        if(!feed.exists()) {
//            feed.mkdir();
//            Log.i("dirMade", "feed");
//        }
//    }
//
//    /**
//     * Check if cached file exists on file system and then loads data from it
//     * while also sending last known publish date to server.
//     * @return
//     */
//    private int checkCache() {
//        File cachedFile = new File(externalDir+"/feed", "feed.json");
//        JSONArray jArr = null;
//        if(cachedFile.exists()) {
//            try {
//                jArr = new JSONArray(JSONFilesIO.readFile(cachedFile));
//                Log.i("LASTP", jArr.getJSONObject(0).getJSONObject("card").getString("publish_on"));
//
//                executorService.submit(new parseMessages(jArr.toString()));
//
//                return Integer.parseInt(jArr.getJSONObject(0).getJSONObject("card").getString("publish_on"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//    }
//
//    private void queueArrays(String data) throws ExecutionException, InterruptedException {
//        executorService.submit(new parseMessages(data));
//    }
//
//    /**
//     * uses a custom object to maintain the data as well as handle any changes to the data
//     * callback sends data to list
//     * @param data
//     * @param fileData
//     * @param operation
//     */
//    private void setContents(List<ModelBase> data, List<String> fileData, String operation) {
//        encapData.setData(data, fileData, operation);
//        callback.setData(encapData.tempData);
//    }
//
//    Handler contentHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            Bundle bundle = msg.getData();
//            setContents((List<ModelBase>) bundle.getSerializable("mb_Data"), bundle.getStringArrayList("file_Strings"), bundle.getString("operationValue"));
//        }
//    };
//
//    /**
//     * Class for parsing the incoming messages to the ModelBase object
//     */
//    class parseMessages implements Runnable {
//        private List<String> threadList;
//
//        private List<String> fileData;
//        private List<ModelBase> data;
//        private String operation;
//
//        parseMessages(List<String> data) {
//            threadList = data;
//        }
//
//        parseMessages(String data) {
//            threadList = new ArrayList<>();
//            threadList.add(data);
//        }
//
//        @Override
//        public void run() {
//            for (String item: threadList) {
//                parseMessage(item);
//            }
//
//            Bundle bundle = new Bundle();
//            bundle.putString("operationValue",this.operation);
//            bundle.putSerializable("mb_Data", (java.io.Serializable) this.data);
//            bundle.putStringArrayList("file_Strings", (ArrayList<String>) this.fileData);
//
//            Message msg = new Message();
//            msg.setData(bundle);
//            contentHandler.sendMessage(msg);
//        }
//
//        /**
//         * Extracts relevant data and assigns into a List of ModelBase
//         * @param message
//         */
//        public void parseMessage(String message) {
//            JSONArray arr = JSONFilesIO.parseToArray(message);
//
//            try {
//                if(!( (JSONValues.contentType(arr.getJSONObject(0)).equals("music")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("post")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("weather")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("traffic")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("bulletin")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("spot")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("callin")) ||
//                        (JSONValues.contentType(arr.getJSONObject(0)).equals("notification")) )) {
//                    return;
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            // Assign the values into the contents list
//            ModelBase mbObj = null;
//            JSONObject obj = null;
//            ArrayList<ModelBase> contents = new ArrayList<>();
//            List<String> fileContents = new ArrayList<>();
//            String operation = "";
//
//            // Adds read data into contents list. Contents list is limited to 20 objects and is a ModeBase object
//            try{
//                for (int i = arr.length()-1; i >= 0; i--) {
//                    obj = arr.getJSONObject(i);
//
//                    if (JSONValues.contentType(obj).equals("music")) {
//                        mbObj = new Music(obj);
//                    }else if (JSONValues.contentType(obj).equals("post")) {
//                        mbObj = new Post(obj);
//                    } else if (JSONValues.contentType(obj).equals("weather")) {
//                        mbObj = new Weather(obj);
//                    } else if (JSONValues.contentType(obj).equals("traffic")) {
//                        mbObj = new Traffic(obj);
//                    } else if (JSONValues.contentType(obj).equals("bulletin")) {
//                        mbObj = new Bulletin(obj);
//                    } else if (JSONValues.contentType(obj).equals("spot")) {
//                        mbObj = new ModelBase(obj);
//                    } else if (JSONValues.contentType(obj).equals("callin")) {
//                        mbObj = new ModelBase(obj);
//                    } else if (JSONValues.contentType(obj).equals("notification")) {
//                        mbObj = new BaseNotification(obj);
//                    }
//
//                    if(obj.getJSONObject("card").has("image_url")) {
//                        mbObj.imageName = StringUtils.uniqueMD5(obj.getJSONObject("card").getString("image_url"));
//                    }  else if(obj.getJSONObject("card").has("thumbnail_url")) {
//                        mbObj.imageName = StringUtils.uniqueMD5(obj.getJSONObject("card").getString("thumbnail_url"));
//                    }
//
//                    mbObj.imageDir = (externalDir+"/images");
//
//                    // Ensures content list does not exceed maximum amount
//                    if((contents.size()+1) > Constants.LIST_LIMIT) {
//                        fileContents.remove(contents.size()-1);
//                        contents.remove(contents.size()-1);
//                    }
//
//                    // Add contents to the front of ArrayList
//                    contents.add(0, mbObj);
//                    fileContents.add(0, obj.toString());
//
//                    operation = mbObj.operation;
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.e("Error Adding contents", e.toString());
//            }
//
//            setContent(contents, fileContents, operation);
//        }
//
//        private void setContent(List<ModelBase> content, List<String> fileContent, String operation) {
//            this.data = content;
//            this.fileData = fileContent;
//            this.operation = operation;
//        }
//    }
//
//    /**
//     * Object class that contains the actual parsed data
//     */
//    private class encapsulatedData {
//        public List<ModelBase> tempData;
//        public List<String> fileData;
//
//        encapsulatedData() {
//            tempData = new ArrayList<>();
//            fileData = new ArrayList<>();
//        }
//
//        public void setData(List<ModelBase> tempData, List<String> tempFileData, String operation) {
//            limitList(this.tempData, this.fileData);
//
//            if(operation.equals("new")) {
//                this.tempData.addAll(0, tempData);
//                this.fileData.addAll(0, tempFileData);
//
//            }else if(operation.equals("update")) {
//                updateItem(tempData.get(0), tempFileData.get(0), this.tempData);
//
//            }else if(operation.equals("delete")) {
//                deleteItem(tempData.get(0), this.tempData);
//
//            }
//        }
//
//        private void limitList(List<ModelBase> data, List<String> fileData) {
//            List<ModelBase> ldata = new ArrayList<>();
//            List<String> lfile =new ArrayList<>();
//            if((data.size()+1) > Constants.LIST_LIMIT) {
//                ldata.addAll(data);
//                lfile.addAll(fileData);
//
//                this.tempData.clear();
//                this.fileData.clear();
//
//                ldata.remove(ldata.size()-1);
//                lfile.remove(lfile.size()-1);
//
//                this.tempData.addAll(ldata);
//                this.fileData.addAll(lfile);
//            }
//        }
//
//        /**
//         * Used when delete operation comes through
//         * Removes data at index
//         * @param data
//         * @param dataList
//         */
//        public void deleteItem(ModelBase data, List<ModelBase> dataList) {
//            List<ModelBase> ldata = new ArrayList<>();
//
//            ldata.addAll(dataList);
//
//            int count = 0;
//            for (ModelBase item : ldata) {
//                if(item.id == data.id && item.type.equals(data.type)) {
//                    this.tempData.remove(count);
//                    this.fileData.remove(count);
//                }
//                count++;
//            }
//        }
//
//        /**
//         * Used when the update operation comes through
//         * Removes data at index and replaces it with new data
//         * @param data
//         * @param fileData
//         */
//        public void updateItem(ModelBase data, String fileData, List<ModelBase> dataList) {
//            List<ModelBase> ldata = new ArrayList<>();
//
//            ldata.addAll(dataList);
//
//            int count = 0;
//            for (ModelBase item : ldata) {
//                if(item.id == data.id && item.type.equals(data.type)) {
//                    this.tempData.remove(count);
//                    this.fileData.remove(count);
//
//                    this.tempData.add(count, data);
//                    this.fileData.add(count, fileData);
//                }
//                count++;
//            }
//        }
//    }
//
//    /**
//     * Writes feed out to file
//     * @throws IOException
//     * @throws JSONException
//     */
//    private void writeToFile() throws IOException, JSONException {
//        File cachedFile = new File(externalDir+"/feed", "feed.json");
//
//        JSONArray arr = new JSONArray();
//        for(String item : encapData.fileData) {
//
//            JSONTokener tokener = new JSONTokener(item);
//            JSONObject obj = new JSONObject(tokener);
//            arr.put(obj);
//        }
//        JSONFilesIO.writeArrayToFile(arr, cachedFile);
//        Log.i("Feed: ", "Written to File");
//    }
//}
