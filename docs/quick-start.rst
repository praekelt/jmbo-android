Quick Start:
============
First thing to do is change the base url to be used by the app. This is located */jmbo-android/app/src/main/java/praekelt/weblistingapp/utils/constants/Constants.java*

.. code-block:: java

    public static final String BASE_URL = "";

The API also needs to be set up to access the listing, this is done in the API.java
*/jmbo-android/app/src/main/java/praekelt/weblistingapp/restfullApi/API.java*

.. literalinclude:: ../app/src/main/java/praekelt/weblistingapp/restfullApi/API.java
    :language: java

The line that needs changing is the @GET for the getListing()

.. literalinclude:: ../app/src/main/java/praekelt/weblistingapp/restfullApi/API.java
    :language: java
    :lines: 13, 14

replace *listing* with the slug of the list the app needs to use

change the string to match the base url of you jmbo site


To include extra model classes you added to the jmbo api:

*  Create a model that extends the provided ModelBase.java in the model package

   */jmbo-android/app/src/main/java/praekelt/weblistingapp/models/extendModelBase/<model-class>.java*


For example this is Post.java that extends the base ModelBase:

.. literalinclude:: ../app/src/main/java/praekelt/weblistingapp/models/extendModelBase/Post.java
    :language: java
    :linenos:



Lines 9-10:
Add the @Expose annotation above the variable name.
The variable name needs to match the name in the incoming json file. Ex: if it was originally called phone_number, the Java file will read:

.. code-block:: java

    @Expose
    private int phone_number


Then merely add the getter and setter methods for said variable. Repeat process for all variables.
This is so that the app can automatically determine the data type and parse the data easily.

Next up you'll want to create the detail view that will display all the information of the newly added class.

*   First create a layout.xml for the desired view

    */jmbo-android/app/src/main/res/layout/fragment_<model-class>_detail.xml*



.. literalinclude:: ../app/src/main/res/layout/fragment_post_detail.xml
    :language: xml
    :linenos:
    :lines: 12-70



Make sure to include views for all the data you need to display and name each appropriately.

*   And then we create a DetailFragment class that extends the ModelBaseDetailFragment.java

    */jmbo-android/app/src/main/java/praekelt/weblistingapp/fragments/detailViews/<model-class>DetailFragment.java*

Continuing with using the Post.java as an example:

.. literalinclude:: ../app/src/main/java/praekelt/weblistingapp/fragments/detailViews/PostDetailFragment.java
    :language: java
    :linenos:



The important parts are, make sure your class extends ModelBaseDetailFragment,
the views are defined and instantiated,
lastly ensure data is set

.. code-block:: java

    // Each view needs to be instantiated for globally in class
    private TextView content;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // fragment_post_detail needs to be replaced with the xml layout you just created
        return inflater.inflate(R.layout.fragment_post_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //each view needs to be instantiated and the content_text needs to be replaced with what you named your view
        content = (TextView) view.findViewById(R.id.content_text);
    }

    public void setData(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        super.setData(obj);

        // Invoke method for each content type to set, getContent is the getter set in the model created earlier, ensure spelling is correct
        Method m = obj.getClass().getMethod("getContent");
        // Set the data for each view
        content.setText(Html.fromHtml((String) (m.invoke(obj))));
    }

*   Finally register these newly creted classes in the Registery.java file.

    */jmbo-android/app/src/main/java/praekelt/weblistingapp/utils/constants/Registry.java*

.. literalinclude:: ../app/src/main/java/praekelt/weblistingapp/utils/constants/Registry.java
    :language: java
    :lines: 18-22, 24-28
    :linenos:



The string key is the class name of the model as defined in jmbo
