# -*- coding: utf-8 -*-

# Run this app with `python 1_simple_app.py` and
# visit http://127.0.0.1:8050/ in your web browser.

import dash
import dash_core_components as dcc
import dash_html_components as html
import plotly.express as px
import pandas as pd
import plotly.graph_objects as go

external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

app = dash.Dash(__name__, external_stylesheets=external_stylesheets)

colors = {
    'background': '#111111',
    'text': '#7FDBFF'
}

# assume you have a "long-form" data frame
# see https://plotly.com/python/px-arguments/ for more options
df = pd.read_excel('Mellat data (10000).xlsx')
print('[INFO]: mellat data was successfully loaded.')
# new_df = df[['sector', 'gender', 'label']]
# new_df = new_df[new_df['sector'] == 1]

fig = go.Figure([go.Bar(x=df['gender'], y=df['sector'])])
fig.update_layout(barmode='group')

# fig = px.bar(df, x="gender", y='sector', color='label',
#              facet_row="sector")

# add colors to the fig

fig.update_layout(
    plot_bgcolor=colors['background'],
    paper_bgcolor=colors['background'],
    font_color=colors['text']
)


app.layout = html.Div(style={'backgroundColor': colors['background']}, children=[
    html.H1(
        children='Hello Dash',
        style={
            'textAlign': 'center',
            'color': colors['text']
        }
    ),

    html.Div(children='Dash: A web application framework for Python.', style={
        'textAlign': 'center',
        'color': colors['text']
    }),

    dcc.Graph(
        id='example-graph-2',
        figure=fig
    )
])

if __name__ == '__main__':
    app.run_server(debug=True, port=8055)
