import dash
import dash_core_components as dcc
import dash_html_components as html
# these are for call backs apperantly
# different than dcc.Input
# dependencies.Input
from dash.dependencies import Input, Output

# add the dash styles to the application
external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']
# initiate the application
app = dash.Dash(__name__, external_stylesheets=external_stylesheets)

# create tha main div for the app and the add stuff in the child nodes.
app.layout = html.Div([
    html.H6("Change the value in the text box to see callbacks in action!"),

    html.Div(["Input: ",
              dcc.Input(id='my-input', value='initial value', type='text')]),
    # div is not needed could go with this
    # dcc.Input(id='my-input', value='initial value', type='text'),
    # GO BRRRRRRRRRRRRRRRR
    html.Br(),
    html.Br(),
    html.Br(),
    # dont set the value here so its filled by the callback.
    html.Div(id='my-output'),
    # html.P(id='my-output'),

])


@app.callback(
    Output(component_id='my-output', component_property='children'),
    Input(component_id='my-input', component_property='value')
)
def update_output_div(input_value):
    return 'Output: {}'.format(input_value)


if __name__ == '__main__':
    app.run_server(debug=True)
