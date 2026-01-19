class Solution {
public:
    vector<vector<int>> insert(vector<vector<int>>& intervals, vector<int>& newInterval) {
        int n = intervals.size(), m = newInterval.size();

        vector<vector<int>> events;
        int i = 0;
        int start, end;
        while (i < n){
            start = intervals[i][0]; // start of an event
            end = intervals[i][1]; // end of an event
            events.push_back({start, 1});
            events.push_back({end, -1});
            i++;
        }

        start = newInterval[0]; // start of an event
        end = newInterval[1]; // end of an event
        events.push_back({start, 1});
        events.push_back({end, -1});

        sort(events.begin(), events.end(), [](auto const &e1, auto const &e2){
            return ((e1[0] != e2[0]) ? e1[0] < e2[0] : e1[1] > e2[1]);
        });

        n = events.size();

        vector<vector<int>> finalIntervals;
        int active = 0, type, point;
        for(i = 0; i < n; i++){
            point = events[i][0];
            type = events[i][1];

            if(active == 0 && type == 1){
                start = point;
            }

            active += type;

            if(active == 0){
                finalIntervals.push_back({start, point});
            }
        }
        
        return finalIntervals;
    }
};