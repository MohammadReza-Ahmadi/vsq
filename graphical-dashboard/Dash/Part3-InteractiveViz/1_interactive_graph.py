import json

import dash
import dash_core_components as dcc
import dash_html_components as html
from dash.dependencies import Input, Output
import plotly.express as px
import pandas as pd

external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

# create the app instance with external styles
app = dash.Dash(__name__, external_stylesheets=external_stylesheets)

# add styles for div rows used later.
styles = {
    'pre': {
        'border': 'thin lightgrey solid',
        'overflowX': 'scroll'
    }
}

# create a dummy dataframe as the dataset.
df = pd.DataFrame({
    "x": [1, 2, 1, 2],
    "y": [1, 2, 3, 4],
    "customdata": [1, 2, 3, 4],
    "fruit": ["apple", "apple", "orange", "orange"]
})

# create a scatter plot from the aforementioned dataset.
fig = px.scatter(df, x="x", y="y", color="fruit", custom_data=["customdata"])

# set some figure obvious properties
fig.update_layout(clickmode='event+select')
fig.update_traces(marker_size=20)

# design the application layout
app.layout = html.Div([
    # the figure is added on top
    dcc.Graph(
        id='basic-interactions',
        figure=fig
    ),

    # on the bottom of the figure create a row to show the interactive results.
    html.Div(className='row', children=[
        # define columns one by one
        html.Div([
            dcc.Markdown("""
                **Hover Data**

                Mouse over values in the graph.
            """),
            html.Pre(id='hover-data', style=styles['pre'])
        ], className='three columns'),

        html.Div([
            dcc.Markdown("""
                **Click Data**

                Click on points in the graph.
            """),
            html.Pre(id='click-data', style=styles['pre']),
        ], className='three columns'),

        html.Div([
            dcc.Markdown("""
                **Selection Data**

                Choose the lasso or rectangle tool in the graph's menu
                bar and then select points in the graph.

                Note that if `layout.clickmode = 'event+select'`, selection data also
                accumulates (or un-accumulates) selected data if you hold down the shift
                button while clicking.
            """),
            html.Pre(id='selected-data', style=styles['pre']),
        ], className='three columns'),

        html.Div([
            dcc.Markdown("""
                **Zoom and Relayout Data**

                Click and drag on the graph to zoom or click on the zoom
                buttons in the graph's menu bar.
                Clicking on legend items will also fire
                this event.
            """),
            html.Pre(id='relayout-data', style=styles['pre']),
        ], className='three columns')
    ])
])


# display different data in unique callbacks referring to the respective children nodes.
@app.callback(
    Output('hover-data', 'children'),
    Input('basic-interactions', 'hoverData'))
def display_hover_data(hoverData):
    return json.dumps(hoverData, indent=2)


@app.callback(
    # here the graph with id="basic-interactions" has a predefined clickData property.
    # that we ll refer to for getting the information.
    Output('click-data', 'children'),
    Input('basic-interactions', 'clickData'))
def display_click_data(clickData):
    return json.dumps(clickData, indent=2)


@app.callback(
    Output('selected-data', 'children'),
    Input('basic-interactions', 'selectedData'))
def display_selected_data(selectedData):
    return json.dumps(selectedData, indent=2)


@app.callback(
    Output('relayout-data', 'children'),
    Input('basic-interactions', 'relayoutData'))
def display_relayout_data(relayoutData):
    return json.dumps(relayoutData, indent=2)


if __name__ == '__main__':
    app.run_server(debug=True, port=8051)
