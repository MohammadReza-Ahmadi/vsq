import dash
import dash_core_components as dcc
import dash_html_components as html
import pandas as pd
import plotly.express as px

external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

# create the app instance with external styles
app = dash.Dash(__name__, external_stylesheets=external_stylesheets)

# load the dataset with the pandas library.
df = pd.read_csv('../Part2-Callbacks/country_indicators.csv')

# extra colors for editing figures.
colors = {
    'background': '#111111',
    'text': '#7FDBFF'
}

available_indicators = df['Indicator Name'].unique()

# design the application layout
app.layout = html.Div(style={'backgroundColor': '#000000'}, children=[
    # the container for the sliders and buttons
    html.Div([
        # container for drop down options
        # this part consists of radio items for choosing the plot type, this can be linear or logarithmic
        # the app has also a drop down for the user to choose,
        # the x and y axis to be plotted on both sides of the plot.

        # first div for the right part
        html.Div([
            dcc.Dropdown(
                id='crossfilter-xaxis-column',
                options=[{'label': i, 'value': i} for i in available_indicators],
                value='Fertility rate, total (births per woman)'
            ),
            dcc.RadioItems(
                id='crossfilter-xaxis-type',
                options=[{'label': i, 'value': i} for i in ['Linear', 'Log']],
                value='Linear',
                # for it to not use two lines for each item.
                labelStyle={'display': 'inline-block'}
            )
        ], style={'width': '49%', 'display': 'inline-block'}),

        # the left part
        html.Div([
            dcc.Dropdown(
                id='crossfilter-yaxis-column',
                options=[{'label': i, 'value': i} for i in available_indicators],
                value='Life expectancy at birth, total (years)'
            ),
            dcc.RadioItems(
                id='crossfilter-yaxis-type',
                options=[{'label': i, 'value': i} for i in ['Linear', 'Log']],
                value='Linear',
                labelStyle={'display': 'inline-block'}
            )
        ], style={'width': '49%', 'float': 'right', 'display': 'inline-block'})

    # style for the whole container
    ], style={
        'borderBottom': 'thin lightgrey solid',
        'backgroundColor': 'rgb(10, 10, 10)',
        'padding': '10px 5px'
    }),

    # the container for the figures
    # two divs first for the complete plot
    # the other one contains two graphs that show the selected point separately.
    html.Div([
        dcc.Graph(
            id='crossfilter-indicator-scatter',
            # choose japan as the default hover?
            hoverData={'points': [{'customdata': 'Japan'}]}
        )
    ], style={'width': '49%', 'display': 'inline-block', 'padding': '0 20'}),
    html.Div([
        dcc.Graph(id='x-time-series'),
        dcc.Graph(id='y-time-series'),
    ], style={'display': 'inline-block', 'width': '49%'}),

    # the year slider than was used in other examples too.
    html.Div(dcc.Slider(
        id='crossfilter-year--slider',
        min=df['Year'].min(),
        max=df['Year'].max(),
        value=df['Year'].max(),
        marks={str(year): str(year) for year in df['Year'].unique()},
        step=None
    ), style={'width': '49%', 'padding': '0px 20px 20px 20px'})
])


# the callback that updates the main figure.
@app.callback(
    dash.dependencies.Output('crossfilter-indicator-scatter', 'figure'),
    [dash.dependencies.Input('crossfilter-xaxis-column', 'value'),
     dash.dependencies.Input('crossfilter-yaxis-column', 'value'),
     dash.dependencies.Input('crossfilter-xaxis-type', 'value'),
     dash.dependencies.Input('crossfilter-yaxis-type', 'value'),
     dash.dependencies.Input('crossfilter-year--slider', 'value')])
def update_graph(xaxis_column_name, yaxis_column_name,
                 xaxis_type, yaxis_type,
                 year_value):

    # get the df that has the requested year.
    dff = df[df['Year'] == year_value]

    # plot only the requested x and y.
    fig = px.scatter(x=dff[dff['Indicator Name'] == xaxis_column_name]['Value'],
                     y=dff[dff['Indicator Name'] == yaxis_column_name]['Value'],
                     hover_name=dff[dff['Indicator Name'] == yaxis_column_name]['Country Name']
                     )

    fig.update_traces(customdata=dff[dff['Indicator Name'] == yaxis_column_name]['Country Name'])

    # update axe types if they were purposely changed.
    fig.update_xaxes(title=xaxis_column_name, type='linear' if xaxis_type == 'Linear' else 'log')
    fig.update_yaxes(title=yaxis_column_name, type='linear' if yaxis_type == 'Linear' else 'log')

    fig.update_layout(margin={'l': 40, 'b': 40, 't': 10, 'r': 0}, hovermode='closest')

    # add colors to the fig
    fig.update_layout(
        plot_bgcolor=colors['background'],
        paper_bgcolor=colors['background'],
        font_color=colors['text'],
    )
    # change the grid colors
    fig.update_xaxes(showline=True, linewidth=2, linecolor='black', gridcolor='#444444')
    fig.update_yaxes(showline=True, linewidth=2, linecolor='black', gridcolor='#444444')

    return fig


# function to create a time series plot from a given pandas dataframe.
def create_time_series(dff, axis_type, title):

    fig = px.scatter(dff, x='Year', y='Value')

    fig.update_traces(mode='lines+markers')

    # turn off the grid on the X or Y axis if needed.
    fig.update_xaxes(showgrid=False)
    fig.update_yaxes(showgrid=False)

    fig.update_yaxes(type='linear' if axis_type == 'Linear' else 'log')

    # add titles to the upper left of the figures.
    fig.add_annotation(x=0, y=0.85, xanchor='left', yanchor='bottom',
                       xref='paper', yref='paper', showarrow=False, align='left',
                       bgcolor='rgba(255, 255, 255, 0.5)', text=title)

    fig.update_layout(height=225, margin={'l': 20, 'b': 30, 'r': 10, 't': 10})

    # add colors to the fig
    fig.update_layout(
        plot_bgcolor=colors['background'],
        paper_bgcolor=colors['background'],
        font_color=colors['text'],
    )
    # change the grid colors
    fig.update_xaxes(showline=True, linewidth=2, linecolor='black', gridcolor='#444444')
    fig.update_yaxes(showline=True, linewidth=2, linecolor='black', gridcolor='#444444')

    return fig


# callback to plot the x timeseries.
@app.callback(
    dash.dependencies.Output('x-time-series', 'figure'),
    [dash.dependencies.Input('crossfilter-indicator-scatter', 'hoverData'),
     dash.dependencies.Input('crossfilter-xaxis-column', 'value'),
     dash.dependencies.Input('crossfilter-xaxis-type', 'value')])
def update_y_timeseries(hoverData, xaxis_column_name, axis_type):
    country_name = hoverData['points'][0]['customdata']
    dff = df[df['Country Name'] == country_name]
    dff = dff[dff['Indicator Name'] == xaxis_column_name]
    title = '<b>{}</b><br>{}'.format(country_name, xaxis_column_name)
    return create_time_series(dff, axis_type, title)


# callback to plot the x timeseries.
@app.callback(
    dash.dependencies.Output('y-time-series', 'figure'),
    [dash.dependencies.Input('crossfilter-indicator-scatter', 'hoverData'),
     dash.dependencies.Input('crossfilter-yaxis-column', 'value'),
     dash.dependencies.Input('crossfilter-yaxis-type', 'value')])
def update_x_timeseries(hoverData, yaxis_column_name, axis_type):
    dff = df[df['Country Name'] == hoverData['points'][0]['customdata']]
    dff = dff[dff['Indicator Name'] == yaxis_column_name]
    return create_time_series(dff, axis_type, yaxis_column_name)


if __name__ == '__main__':
    # app.run_server(debug=True, port=8052)

    # to listen on all ports run this one
    app.run_server(debug=False, host='0.0.0.0', port=8050)
