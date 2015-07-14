Initial Setup
=============

Android Studio
---------------

This is under the assumption Android Studio, Android SDK and Java are all set up and working.

clone jmbo-android repo to a directory of your choice.
Launch Android Studio,
select import project
and select the root directory. If anything does go wrong, reimport, this time select the build.gradle file in the root.

The project should now be open and fully functioning in Android Studio.

The basics
----------

This app is designed to work with the jmbo api. The basis is, anything you add or create in jmbo should be
displayable by the app itself.
The app uses json's provided by the jmbo api to aquire all the needed information.
Out of the box it supports, ModelBase, Post and Video content types.

To add new content types is a fairly easy process.

**Important note, at this stage the app only supports listings with the content added to them, and currently listings cannot display other listings**

First thing to check is the BASE_URL string set in the Constants.java

*/jmbo-android/app/src/main/java/praekelt/weblistingapp/utils/constants/Constants.java*

.. code-block:: java

    // The base jmbo url
    public static final String BASE_URL = "http://www.jmbo-demo.org/"

The url will generally look like this: *http://www.jmbo-demo.org/listing/main/*
The app needs everything from the http to the last trailing slash after the .org/.com/.co.za/.fr/etc.

**Please ensure it includes the trailing slash.**

The API also needs to be set up to access the listing, this is done in the API.java
*/jmbo-android/app/src/main/java/praekelt/weblistingapp/restfullApi/API.java*

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/restfullApi/API.java
    :language: java

The line that needs changing is the @GET for the getListing()

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/restfullApi/API.java
    :language: java
    :lines: 13, 14

replace *listing* with the slug of the list the app needs to use

Add new content type
--------------------

This is the process to add a new content type if the type extends ModelBase and djangos own Model.

The app, out of the box, supports Post, Video and ModelBase content types. So adding new types is only needed for anything else.

This can be done in three steps.

*   :ref:`add-model`
*   :ref:`add-detail-view`
*   :ref:`register-classes`

.. _add-model:

Add the content type model
--------------------------

Create a new model java class, for consistency's sake give it the same name used in jmbo, keeping it in line with java's style guide (Classes are UpperCamelCase)
This new class should extend ModelBase.java and should only include any new content types added.

For example, here we have the Post.

*/jmbo-android/app/src/main/java/praekelt/weblistingapp/models/extendModelBase/Post.java*

The only new content type the post has is called *content* and is a Html styled rich text. But is still at its basis a String. (Html markup parsing and other actions are done in the DetailView classes not here)

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/models/extendModelBase/Post.java
    :language: java


Add the @Expose annotation above the variable name.
The variable name needs to match the name in the incoming json file. Ex: if it was originally called phone_number

.. code-block:: java

    @Expose
    private int phone_number

Alternatively @SerializedName can be used so the variable naming convention will match java's style guie:

.. code-block:: java

    // This however adds unnecessary extra code. As this class will never be directly accessed by the developer again, it can safely be omitted.
    @SerializedName("phone_number")
    @Expose
    private int phoneNumber;

Then merely add the getter and setter methods for said variable. Repeat process for all variables.
This is so that the app can automatically determine the data type and parse the data easily.

This does however require knowledge of the json variables that will be sent to the app.
These sites might be of assistance:

*   http://www.jsonschema2pojo.org/:
        * Will generate the full class automatically for you, simply ensure *Source Type* is set to JSON and *Annotation style* is set to GSON.

*   http://jsonviewer.stack.hu/:
        * Will alternatively give you the variable types and then allow you to add these manually.

In both cases, please do not inlude any ModelBase content types.
As the only json output you will find from jmbo is more than likely to incude the ModelBase content as well.

.. _add-detail-view:

Create layout and fragment
--------------------------

Create a new xml layout first. Making sure to include a view for all the content types that need to be displayed.
Since xml layouts cannot be extended like Java classes, each content type view will have to be created even if it is a ModelBase type.

*   First create a layout.xml for the desired view
        */jmbo-android/app/src/main/res/layout/fragment_<model-class>_detail.xml*

Here is an example of the fragment_post_detail.xml view layout:

.. literalinclude:: ../../app/src/main/res/layout/fragment_post_detail.xml
    :language: xml
    :lines: 12-70

Always add the title, timestamp and image view. Using the same ids for those three.

**Remember to add ids to every view so they can be instantiated and used in the Java class**

*   Next create a DetailFragment that extends ModelBaseDetail.java
        * /jmbo-android/app/src/main/java/praekelt/weblistingapp/fragments/detailViews/<model-class>DetailFragment.java 

Here is an example of PostDetailFragment.java:

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/fragments/detailViews/PostDetailFragment.java
    :language: java
    :emphasize-lines: 17, 32-36, 38-42, 48-53

In most average cases only three methods will ever be used.

*   onCreateView()

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/fragments/detailViews/PostDetailFragment.java
    :language: java
    :lines: 35

*fragment_post_detail* will be replaced by the layout created earlier.

.. code-block:: java

    return inflater.inflate(R.layout.fragment_<model-class>_detail, container, false);

*   onViewCreated()

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/fragments/detailViews/PostDetailFragment.java
    :language: java
    :lines: 17, 38-42

Each view will need to be declared globally for the class.
then in onViewCreated they will each need to be initialised.

*   setData()

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/fragments/detailViews/PostDetailFragment.java
    :language: java
    :lines: 48-53

The *obj* item is the parsed json in the form of a java object.

A new method needs to be invoked, with the string inside getMethod("") being the method from the Model created earlier.
Please ensure spelling of string as most IDEs won't be able to give you auto-completion hints and miss spelling the method name will crash the app and raise an exception.

.. code-block:: java

        Method m = obj.getClass().getMethod("<getter-method-name>");

Each view will need to have its data set. This is done by invoking the method.

.. code-block:: java

    content.setText(Html.fromHtml((String) (m.invoke(obj))));

As seen here this class is where any additional manipulation of the data will occur.

So once more in short:

*   In onCreateView() merely the view that is being inflated needs to be set.
*   In onViewCreated() all the views from the layout need to be instantiated.
*   In setData() all the views need to be filled with data aquired from the jmbo api.


.. _register-classes:

Register classes in Registry
----------------------------

Finally add the two classes to the two methods located in the registry, ensuring the string key is hte same as the class name from the original jmbo model.

.. literalinclude:: ../../app/src/main/java/praekelt/weblistingapp/utils/constants/Registry.java
    :language: java
    :lines: 18-22, 24-28

This is a simple procedure in *TYPE_CLASS* under the already existing values add:

.. code-block:: java

    put("<ClassName>", <Model>.class);

And in *DETAIL_CLASS* add:

.. code-block:: java

    put("<ClassName>", <Model>DetailFragmnt.class);

