# -*- coding: utf-8 -*-

# Run this app with `python 1_simple_app.py` and
# visit http://127.0.0.1:8050/ in your web browser.

import dash
import dash_core_components as dcc
import dash_html_components as html
import pandas as pd
import plotly.figure_factory as ff
from dash.dependencies import Input, Output
from scipy import stats
import numpy as np
# from flask import Flask
# from flask_restful import Resource, Api


external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

# server = Flask(__name__)
# app = dash.Dash(server=server, external_stylesheets=external_stylesheets)
# api = Api(server)

app = dash.Dash(__name__, external_stylesheets=external_stylesheets)

# read the data.
df = pd.read_excel('Mellat data (10000).xlsx')
# remove irrelevant features.
df.drop(columns=['id'], inplace=True)
# color for the first figure.
colors = ['#333F44']
fig = ff.create_distplot([df['age ']], ['Distribution'], colors=colors)

# get the features that are numeric.
numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
# and also have some variation in them.
col_names = [col for col in df.columns if df[col].dtype in numerics]
# uniqueness check, so that a feature like gender is not plotted.
col_names = [col for col in col_names if len(np.unique(df[col])) > 10]
df = df[col_names]
# zscore to remove the outliers.
df = df[(np.abs(stats.zscore(df)) < 3).all(axis=1)]


app.layout = html.Div(children=[
    html.H2(
        children='Distribution in Mellat Data',
        style={
            'textAlign': 'center',
        }
    ),

    html.Div(children='.', style={
        'textAlign': 'center',
    }),

    dcc.Graph(
        id='dist-graph',
        figure=fig
    ),
    html.Br(),
    html.P(children="Displayed Feature:"),
    html.Br(),
    dcc.RadioItems(
        id='column-name',
        options=[{'value': x, 'label': x}
                 for x in col_names],
        value='age ',
        labelStyle={'display': 'inline-block'}
    ),
])


@app.callback(
    Output("dist-graph", "figure"),
    Input("column-name", "value"))
def generate_chart(column_name):
    color_vec = np.random.randint(0, 15, size=6)
    color_ref = ['1', '2', '3', '4', '5', '6', '7', '8', '9',
                 'A', 'B', 'C', 'D', 'E', 'F']
    color_vec = [color_ref[ind] for ind in color_vec]
    random_colors = ["#" + ''.join(color_vec)]
    temp_df = df[column_name]
    figure = ff.create_distplot([temp_df], [column_name], colors=random_colors)
    return figure


if __name__ == '__main__':
    app.run_server(debug=True, port=8055)
