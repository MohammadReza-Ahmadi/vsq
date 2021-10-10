import base64
import io
import dash
from dash.dependencies import Input, Output, State
import dash_core_components as dcc
import dash_html_components as html
import pandas as pd
import plotly.figure_factory as ff
from scipy import stats
import numpy as np


# max csv size to plot
MAX_SAMPLE_SIZE = 10000

# max features to plot
MAX_FEATURES = 10

# min unique digits for a features to be plottable
MIN_UNIQUE = 10

# external style css
external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

# create an application instance
app = dash.Dash(__name__, external_stylesheets=external_stylesheets,
                prevent_initial_callbacks=True)

# load a dummy dataset to show at first glance
df = pd.DataFrame(np.random.randn(1000, 11) * 10)

# get the column names for creating a radio button to select from later.
col_names = df.columns

# create the application layout
app.layout = html.Div([
    
    dcc.Upload(
        id="upload-data",
        children=html.Div([
        'Drag and Drop or ',
        html.A("Select Files")
    ]),
        
    style={
    'width': '90%',
    'height': '90%',
    'lineHeight': '60px',
    'borderWidth': '1px',
    'borderStyle': 'dashed',
    'borderRadius': '5px',
    'textAlign': 'center',
    'margin': '10px',
    },
        
    # Do not allow multiple files to be uploaded
    # Since we process datasets separately
    multiple=True
    ),

    # create a div for the output plots
    html.Div(id='output-data-upload'),

    dcc.Graph(
        id='dist-graph',
        figure=ff.create_distplot([df.iloc[:, 1]], ['no data'], show_rug=False, show_curve=True)
    ),

    html.Br(),

    # Show the name of the feature being plotted.
    html.P(children="Displayed Feature:"),
    
    html.Br(),

    # Create a radio button for the user to select among the features.
    dcc.Dropdown(
        id='column-name',
        options=[{'value': x, 'label': x}
                 for x in col_names],
        value=col_names[0],
        # labelStyle={'display': 'inline-block'}
    ),

])


# Load the csv and excel from the posted data.
def parse_contents(contents, filename, date):
    # separate the content type and the content
    content_type, content_string = contents.split(',')

    # decode the base64 data
    decoded = base64.b64decode(content_string)

    # define global variables for the dataframe and the column names to later be used
    # by onclick events
    global df
    global col_names

    try:

        # if it is a csv file pandas read_csv is used
        if 'csv' in filename:
            # Assume that the user uploaded a CSV file
            df = pd.read_csv(
                io.StringIO(decoded.decode('utf-8')))

        # # if it is a csv file pandas read_excel is used
        elif 'xls' in filename:
            # Assume that the user uploaded an excel file
            df = pd.read_excel(io.BytesIO(decoded))

        # if the format is unsupported then the df is passed as empty.
        else:
            df = None

        # trim the dataset if the sample size is bigger than the global defined size
        if len(df) >= MAX_SAMPLE_SIZE:
            df = df.iloc[:MAX_SAMPLE_SIZE, :]
            print(f'TRIMMING DATA TO: {df.shape}')

        # Get the numeric columns that we can plot using a distribution plot
        numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
        col_names = [col for col in df.columns if df[col].dtype in numerics]

        # uniqueness check, so that a feature like gender is not plotted.
        # the feature must have more than a predefined number of unique values to be
        # in the list of plottable features.
        col_names = [col for col in col_names if len(np.unique(df[col])) > MIN_UNIQUE]

        # trim the features if the size is more than the global predefined value.
        if len(col_names) >= MAX_FEATURES:
            col_names = col_names[0:MAX_FEATURES]
        df = df[col_names]

        # fill the NAN and NULL values in the dataframe with the mean strategy
        for col in col_names:
            df[col] = df[col].fillna(value=df[col].mean())

        # use z-score to remove the outliers.
        df = df[(np.abs(stats.zscore(df)) < 3).all(axis=1)]

        # show the user the uploaded file name to indicate that the file was successfully processed.
        return html.Div([
            f'Current Data: {filename}'
        ])

    except Exception as e:
        print(e)

        # show the error to the client if there was problems with the dataset.
        return html.Div([
            'There was an error processing this file.'
        ])


# this is the callback that loads the new data uploaded with the button
# then attempts the radio button elements based on the provided dataframe
@app.callback(
    Output('output-data-upload', 'children'),
    Output('column-name', 'options'),
    Output('column-name', 'value'),
    [Input('upload-data', 'contents')],
    [State('upload-data', 'filename'),
     State('upload-data', 'last_modified')])
def update_output(list_of_contents, list_of_names, list_of_dates):
    if list_of_contents is not None:
        children = [parse_contents(c, n, d) for c, n, d in zip(list_of_contents, list_of_names, list_of_dates)]
        opts = [{'value': x, 'label': x} for x in col_names]
        val = col_names[0]
        return children, opts, val


# this callback updates the figure each time the selected feature
# in the radio button changes.
@app.callback(
    Output("dist-graph", "figure"),
    Input("column-name", "value"))
def generate_chart(column_name):
    # generate random colors for more interpretability each time.
    color_vec = np.random.randint(0, 9, size=6)
    color_ref = ['1', '2', '3', '4', '5', '6', '7', '8', '9']
    color_vec = [color_ref[ind] for ind in color_vec]
    random_colors = ["#" + ''.join(color_vec)]
    # plot the requested column from the original dataframe
    temp_df = df[column_name]
    figure = ff.create_distplot([temp_df], [column_name], colors=random_colors, show_curve=True,
                                show_rug=False, show_hist=True)
    return figure


if __name__ == "__main__":
    app.run_server(host="0.0.0.0", port=8050, debug=False)
