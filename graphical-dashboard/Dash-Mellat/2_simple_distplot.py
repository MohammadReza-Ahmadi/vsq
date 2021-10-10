# -*- coding: utf-8 -*-

# Run this app with `python 1_simple_app.py` and
# visit http://127.0.0.1:8050/ in your web browser.
# TODO: create an application that based on dtype suggests rows to select for an upgraded distplot.

import dash
import dash_core_components as dcc
import dash_html_components as html
import pandas as pd
import plotly.figure_factory as ff

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

fig = ff.create_distplot([df['age ']], ['Distribution'])
#
# numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
# new_df = df.select_dtypes(include=numerics)
# new_df = new_df[['age ', 'interest rate']]
# fig = ff.create_distplot([new_df[c] for c in new_df.columns], new_df.columns)

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
        children='Age Distribution in Mellat Data',
        style={
            'textAlign': 'center',
            'color': colors['text']
        }
    ),

    html.Div(children='.', style={
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
