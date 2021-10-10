# Run this app with `python app.py` and
# visit http://127.0.0.1:8050/ in your web browser.

import dash
import dash_html_components as html
import pandas as pd

df = pd.read_excel('Mellat data (10000).xlsx')
# Here we can have a slider or a feature to indicate the rows that we want to see.
df = df.iloc[0:10, :]


def generate_table(dataframe, max_rows=10):
    return html.Table([
        html.Thead(
            html.Tr([html.Th(col) for col in dataframe.columns])
        ),
        html.Tbody([
            html.Tr([
                html.Td(dataframe.iloc[i][col]) for col in dataframe.columns
            ]) for i in range(min(len(dataframe), max_rows))
        ])
    ])


external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

app = dash.Dash(__name__, external_stylesheets=external_stylesheets)

app.layout = html.Div(children=[
    html.H4(children='MELLAT BANK DATASET', style={'textAlign': 'center'}),
    generate_table(df)
])

if __name__ == '__main__':
    app.run_server(debug=True, port=8053)
