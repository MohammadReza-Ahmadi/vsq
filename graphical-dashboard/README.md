# graphical-dashboard


This Repository Presents a simple benchmark between different plotting libraries namely:

* [Bokeh](https://bokeh.org/)

* [D3](https://d3js.org/)

* [Dash](https://plotly.com/dash/)

* [Matplotlib](https://matplotlib.org/)

* [Seaborn](https://seaborn.pydata.org/)

* [Pygal](http://www.pygal.org/)


## Preliminaries
Install the requirements via pip.
```bash
git clone https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard
cd graphical-dashboard
pip install -r requirements.txt
```

Note that specific projects might also have extra dependencies to later be installed.

Most of the project are presented in either jupyter-notebooks or flask endpoints for a better interpretation.



* **Bokeh:**  
Bokeh is a visualization library which uses bokeh.js, that is a wrapper on d3.js the famous visualization library. The authors browser dashboards and graphs in mind. The web backend used for the library is Tornado. Also bokeh requires to write some interactive components in JavaScript.
The library provides many interactive plots and unclick events, although the support seems less than js libraries or plotly-dash.
It is still one of the mostly used frameworks in python.

    * [Flask Application](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Bokeh/FlaskBokeh)
    * [Notebooks](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Bokeh/Notebooks)
        * [Introduction](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/Notebooks/00_Introduction_and_Setup.ipynb)
        * [Basic Plotting](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/Notebooks/01_Basic_Plotting.ipynb)
        * [Styles and Themes](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/Notebooks/02_Styling_and_Theming.ipynb)
        * [Transformations](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/Notebooks/03_Data_Sources_and_Transformations.ipynb)
        * [Annotations](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/Notebooks/04_Adding_Annotations.ipynb)
        * [Layouts](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/Notebooks/05_Presentation_Layouts.ipynb)
    * [Quickstart](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Bokeh/bokeh_quickstart.ipynb)


* **D3:** 
D3.js is a JavaScript library for producing dynamic, interactive data Visualizations in web browsers. It makes use of Scalable Vector Graphics, HTML5, and Cascading Style Sheets standards. It is the successor to the earlier Protovis framework.
In general D3 has much more flexibility and interactivity than its python counter parts. The problem with D3 is that it is quite complicated as a general framework, and working with this great library requires more time and work.

    * [Flask Application](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/D3)
        * [Pie Chart](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/D3/static/js/pieChart.js)
        * [Bar Chart](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/D3/static/js/barChart.js)
        * [Updating Charts](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/D3/static/js/updateChart.js)



* **Dash:** 
"Dash is a productive Python framework for building web analytic applications. Written on top of Flask, Plotly. js, and React. js, Dash is ideal for building data visualization apps with highly custom user interfaces in pure Python. It's particularly suited for anyone who works with data in Python."
In conclusion Dash is the best interactive library in python which provides you with custiom application Layouts. it also works with flask which further improves the readibility. Its also easy to add new features to a flask based application. HTML and CSS is also fully configurable from Dash. The only downside is HTML and CSS updates, less flexibility and samller community than D3. In the end as it is built with Machine Learing and Data Science projects in mind its a great option.

    * [Layout](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Dash/Part1-Layout)
        * [Simple App](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part1-Layout/1_simple_app.py)
        * [Reusable Elements](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part1-Layout/2_reusable_elems_app.py)
        * [Scatter Plots](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part1-Layout/3_scatter.py)
        * [Markdown](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part1-Layout/4_markdown_app.py)
        * [Dash Core](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part1-Layout/5_dash_core.py)

    * [Callbacks](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Dash/Part2-Callbacks)
        * [Simple Interaction](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/1_simple_interaction.py)
        * [Updating Figures](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/2_updating_figures.py)
        * [Multiple Argument Callbacks](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/3_multiple_arg_callbacks.py)
        * [Multiple Outputs](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/4_multiple_outputs.py)
        * [Chained Events](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/5_chained.py)
        * [Instant Event](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/6_form_like_event.py)
        * [Form Like Event](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part2-Callbacks/7_form_like_event_working.py)

    * [Interactive](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Dash/Part3-InteractiveViz)
        * [Graph](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part3-InteractiveViz/1_interactive_graph.py)
        * [Updating Graph](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part3-InteractiveViz/2_update_graph.py)
        * [Generic Crossfilter](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Dash/Part3-InteractiveViz/3_generic_crossfilter.py)


* **Matplotlib-Seaborn:** 
Matplotlib is mainly deployed for basic plotting. Visualization using Matplotlib generally consists of bars, pies, lines, scatter plots and so on.
Seaborn, on the other hand, provides a variety of visualization patterns. It uses fewer syntax and has easily interesting default themes. It specializes in statistics visualization and is used if one has to summarize data in visualizations and also show the distribution in the data. 
Matplotlib and Seaborn are great in a sense that they are easy too apply on the data. There are hundreds of examples available for almost any category since they are the most used libraries in python. The also provide SVG outputs which are great for browser rendering and applications. The downside to them though is that they do not provide any interactive plots at all. Also plots with large number of features and lengthy column names is less optimized.

* [Seaborn Notebooks](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Matplotlib-Seaborn)
    * [General Tutorial](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Matplotlib-Seaborn/Seaborn_Tutorial.ipynb)
    * [Mellat Data Visualization](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Matplotlib-Seaborn/Mellat_Seaborn.ipynb)
    * [German Credit Visualization](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/blob/master/Matplotlib-Seaborn/German_Seaborn.ipynb)
    


* **Pygal:** 

When it comes to visualizing data in Python, most data scientists go with the infamous Matplotlib, Seaborn, or Bokeh. However, one of the libraries that are often overlooked is Pygal. Pygal allows the user to create beautiful interactive plots that can be turned into SVGs with an optimal resolution for printing or being displayed on webpages using Flask or Django.
* [Flask Application](https://gitlab.com/vosouq/backend/benchmark/graphical-dashboard/-/tree/master/Pygal)










